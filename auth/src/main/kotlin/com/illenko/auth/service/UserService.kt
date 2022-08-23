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

    fun saveUser(user: User): User {
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    fun auth(user: User) {
        val savedUser = userRepository.findByUsername(user.username)
        if (savedUser == null) {
            throw BadCredentialsException("Username or password is wrong")
        } else {
            if (passwordEncoder.matches(passwordEncoder.encode(savedUser.password), user.password)) {
                renewOtp(user)
            } else {
                throw BadCredentialsException("Username or password is wrong")
            }
        }
    }

    fun checkOtp(otpToValidate: Otp): Boolean =
        otpRepository.findByUsername(otpToValidate.username)?.let {
            return it.code == otpToValidate.code
        } ?: false

    private fun renewOtp(user: User) {
        val savedOtp = otpRepository.findByUsername(user.username)
        val code = GenerateOtpUtil.generateCode()

        if (savedOtp == null) {
            val otp = Otp(user.username, code)
            otpRepository.save(otp)
        } else {
            savedOtp.code = code
            otpRepository.save(savedOtp)
        }
    }
}