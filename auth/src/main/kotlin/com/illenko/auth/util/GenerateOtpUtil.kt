package com.illenko.auth.util

import java.security.SecureRandom

object GenerateOtpUtil {
    fun get() = (SecureRandom.getInstanceStrong().nextInt(9000) + 1000).toString()
}