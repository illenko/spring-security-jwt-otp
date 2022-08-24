package com.illenko.gateway.config.security.filter

import com.illenko.gateway.config.security.token.OtpAuthentication
import com.illenko.gateway.config.security.token.UsernamePasswordAuthentication
import com.illenko.gateway.properties.AuthProperties
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.filter.OncePerRequestFilter
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class InitialAuthenticationFilter(
    private val manager: AuthenticationManager,
    private val authProperties: AuthProperties
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest) = request.servletPath != "/login"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val username = request.getHeader(USER_NAME)
        val password = request.getHeader(PASSWORD)
        val code = request.getHeader(CODE)

        code?.let { otp(username, code, response) } ?: auth(username, password)
    }

    private fun auth(username: String, password: String) {
        manager.authenticate(UsernamePasswordAuthentication(username, password))
    }

    private fun otp(username: String, code: String, response: HttpServletResponse) {
        val auth = OtpAuthentication(username, code)
        manager.authenticate(auth)
        response.setHeader(AUTHORIZATION, getJwt(username))
    }

    private fun getJwt(username: String) =
        Jwts.builder().setClaims(mapOf(USER_NAME to username)).signWith(getKey()).compact()

    private fun getKey() = Keys.hmacShaKeyFor(authProperties.secret.toByteArray(StandardCharsets.UTF_8))

    companion object {
        private const val USER_NAME = "username"
        private const val PASSWORD = "password"
        private const val CODE = "code"
        private const val AUTHORIZATION = "Authorization"
    }
}