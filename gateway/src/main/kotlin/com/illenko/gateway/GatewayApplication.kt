package com.illenko.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.illenko.gateway")

class GatewayApplication

fun main(args: Array<String>) {
	runApplication<GatewayApplication>(*args)
}
