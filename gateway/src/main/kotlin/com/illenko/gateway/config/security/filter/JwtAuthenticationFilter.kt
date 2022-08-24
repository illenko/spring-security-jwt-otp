package com.illenko.gateway.config.security.filter

import com.illenko.gateway.config.security.token.UsernamePasswordAuthentication
import com.illenko.gateway.properties.AuthProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(private val authProperties: AuthProperties) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest) = request.servletPath == "/login"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val claims = getClaims(getKey(), request.getHeader(AUTHORIZATION))
        val username = claims[USER_NAME].toString()

        val auth = UsernamePasswordAuthentication(username, null, listOf(SimpleGrantedAuthority(USER)))

        SecurityContextHolder.getContext().authentication = auth
        filterChain.doFilter(request, response)
    }

    private fun getClaims(key: SecretKey, jwt: String) =
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).body


    private fun getKey() = Keys.hmacShaKeyFor(authProperties.secret.toByteArray(StandardCharsets.UTF_8))

    companion object {
        private const val USER_NAME = "username"
        private const val USER = "user"
        private const val AUTHORIZATION = "Authorization"
    }
}