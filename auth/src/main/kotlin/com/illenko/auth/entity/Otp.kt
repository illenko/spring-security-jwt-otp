package com.illenko.auth.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Otp(@Id val username: String, var code: String)