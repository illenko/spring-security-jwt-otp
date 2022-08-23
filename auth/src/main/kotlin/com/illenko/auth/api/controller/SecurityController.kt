package com.illenko.auth.api.controller

import com.illenko.auth.entity.Otp
import com.illenko.auth.entity.User
import com.illenko.auth.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class SecurityController(private val userService: UserService) {

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    fun addUser(@RequestBody user: User) {
        userService.save(user)
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    fun auth(@RequestBody user: User) {
        userService.save(user)
    }

    @PostMapping("/otp/check")
    fun checkOtp(@RequestBody otp: Otp, response: HttpServletResponse) {
        response.status = if (userService.check(otp)) HttpServletResponse.SC_OK else HttpServletResponse.SC_FORBIDDEN
    }
}