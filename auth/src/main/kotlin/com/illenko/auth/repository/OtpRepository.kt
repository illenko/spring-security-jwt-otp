package com.illenko.auth.repository

import com.illenko.auth.entity.Otp
import org.springframework.data.jpa.repository.JpaRepository

interface OtpRepository : JpaRepository<Otp, String> {
    fun findByUsername(username: String): Otp?
}