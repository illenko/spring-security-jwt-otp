package com.illenko.auth.entity

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(@Id val username: String, var password: String)