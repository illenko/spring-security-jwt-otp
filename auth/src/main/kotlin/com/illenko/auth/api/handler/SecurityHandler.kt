package com.illenko.auth.api.handler

import com.illenko.auth.service.UserService
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.body

@Component
class SecurityHandler(private val userService: UserService) {

    fun addUser(request: ServerRequest) {
        userService.saveUser(request.body())
    }

    fun auth(request: ServerRequest) {
        userService.auth(request.body())
    }

    fun checkOtp(request: ServerRequest) {
        userService.checkOtp(request.body())
    }
}