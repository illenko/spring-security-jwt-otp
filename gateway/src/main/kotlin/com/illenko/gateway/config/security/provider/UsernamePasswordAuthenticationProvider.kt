package com.illenko.gateway.config.security.provider

import com.illenko.gateway.config.security.token.UsernamePasswordAuthentication
import com.illenko.gateway.security.AuthClient
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class UsernamePasswordAuthenticationProvider(private val authClient: AuthClient) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials.toString()

        authClient.auth(username, password)

        return UsernamePasswordAuthenticationToken(username, password)
    }

    override fun supports(authentication: Class<*>) =
        UsernamePasswordAuthentication::class.java.isAssignableFrom(authentication)

}