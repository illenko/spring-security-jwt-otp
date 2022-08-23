package com.illenko.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ProjectConfig {

    @Bean
    fun restTemplate() = RestTemplate()
}