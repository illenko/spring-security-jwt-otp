package com.illenko.auth.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user", catalog = "auth", schema = "public")
data class User(@Id var username: String, var password: String)