package com.illenko.auth.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(@Id var username: String, var password: String)