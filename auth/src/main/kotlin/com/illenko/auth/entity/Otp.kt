package com.illenko.auth.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "otp", catalog = "auth", schema = "public")
data class Otp(@Id var username: String, var code: String)