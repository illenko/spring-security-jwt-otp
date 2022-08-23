package com.illenko.auth.service

import com.illenko.auth.entity.Otp
import com.illenko.auth.entity.User
import com.illenko.auth.repository.OtpRepository
import com.illenko.auth.repository.UserRepository
import com.illenko.auth.util.GenerateOtpUtil
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val otpRepository: OtpRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun save(user: User) = userRepository.save(user.apply { password = passwordEncoder.encode(user.password) })

    fun auth(user: User) {
        userRepository.findByUsername(user.username)?.let {
            if (passwordEncoder.matches(user.password, it.password)) {
                renewOtp(user)
            } else {
                throw BadCredentialsException("Username or password is wrong")
            }
        } ?: throw BadCredentialsException("Username or password is wrong")
    }

    fun check(otp: Otp) =
        otpRepository.findByUsername(otp.username)?.let { it.code == otp.code } ?: false

    private fun renewOtp(user: User) {
        otpRepository.findByUsername(user.username)?.apply { code = GenerateOtpUtil.get() } ?: Otp(
            user.username,
            GenerateOtpUtil.get()
        )
    }
}