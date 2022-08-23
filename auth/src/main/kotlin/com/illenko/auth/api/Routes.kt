package com.illenko.auth.api

import com.illenko.auth.api.handler.SecurityHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.router

@Configuration
class Routes(private val securityHandler: SecurityHandler) {

    @Bean
    fun route() = router {
        (accept(APPLICATION_JSON)).nest {
            POST(USER_ADD) { ok().body(securityHandler::addUser) }
            // TODO. return status from handler
            POST(USER_AUTH) { ok().body(securityHandler::auth) }
            // TODO. return status from handler
            POST(OTP_CHECK) { ok().body(securityHandler::checkOtp) }
        }
    }

    companion object {
        const val USER_ADD = "/user/add"
        const val USER_AUTH = "/user/auth"
        const val OTP_CHECK = "/otp/check"
    }

}