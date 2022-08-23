package com.illenko.gateway.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DataController {
    
    @GetMapping("/test")
    fun test() = "Test!"
    
}