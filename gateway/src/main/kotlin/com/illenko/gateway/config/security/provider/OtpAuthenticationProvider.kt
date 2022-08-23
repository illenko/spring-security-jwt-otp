package com.illenko.gateway.config.security.provider

import com.illenko.gateway.config.security.token.OtpAuthentication
import com.illenko.gateway.security.AuthClient
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class OtpAuthenticationProvider(private val authClient: AuthClient) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val code = authentication.credentials.toString()

        return if (authClient.otp(username, code))
            OtpAuthentication(username, code)
        else throw BadCredentialsException("Wrong username or otp")
    }

    override fun supports(authentication: Class<*>) = OtpAuthentication::class.java.isAssignableFrom(authentication)
}