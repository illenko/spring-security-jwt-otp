package com.illenko.auth.entity

import javax.persistence.Id

data class Otp(@Id val username: String, var code: String)