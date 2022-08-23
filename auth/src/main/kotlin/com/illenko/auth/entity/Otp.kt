package com.illenko.auth.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Otp(@Id var username: String, var code: String)