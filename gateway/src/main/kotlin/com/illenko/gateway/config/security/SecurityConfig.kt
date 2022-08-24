package com.illenko.gateway.config.security

import com.illenko.gateway.config.security.filter.InitialAuthenticationFilter
import com.illenko.gateway.config.security.filter.JwtAuthenticationFilter
import com.illenko.gateway.config.security.provider.OtpAuthenticationProvider
import com.illenko.gateway.config.security.provider.UsernamePasswordAuthenticationProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class SecurityConfig(
    private val initialAuthenticationFilter: InitialAuthenticationFilter,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val otpAuthenticationProvider: OtpAuthenticationProvider,
    private val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(usernamePasswordAuthenticationProvider)
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.addFilterAt(initialAuthenticationFilter, BasicAuthenticationFilter::class.java)
            .addFilterAfter(jwtAuthenticationFilter, BasicAuthenticationFilter::class.java)

        http.authorizeRequests().anyRequest().authenticated()
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }
}