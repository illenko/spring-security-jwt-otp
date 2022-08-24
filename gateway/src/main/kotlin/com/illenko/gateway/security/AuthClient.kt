package com.illenko.gateway.security

import com.illenko.gateway.dto.User
import com.illenko.gateway.properties.AuthProperties
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class AuthClient(
    private val rest: RestTemplate,
    private val authProperties: AuthProperties
) {
    fun auth(username: String, password: String) {
        rest.postForEntity(
            "${authProperties.url}/auth",
            HttpEntity(User(username = username, password = password)),
            Unit::class.java
        )
    }

    fun otp(username: String, code: String) =
        rest.postForEntity(
            "${authProperties.url}/otp/check",
            HttpEntity(User(username = username, code = code)),
            Unit::class.java
        ).statusCode == HttpStatus.OK
}