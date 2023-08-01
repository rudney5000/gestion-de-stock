package com.dedytech.springsecuritykotlin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService
    ): WebSecurityConfiguration() {

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val provider = DaoAuthenticationProvider()
        provider.setUserDetailsService(userDetailsService)
        return provider
    }

    @Bean
    fun jwtAuthFilter(authenticationManager: AuthenticationManager): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(authenticationManager)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val jwtAuthFilter = jwtAuthFilter(authenticationManager())
        val authenticationProvider = authenticationProvider()
        http
            .csrf()
            .disable()
            .authorizeHttpRequests()
             .requestMatchers("")
             .permitAll()
             .anyRequest()
             .authenticated()
             .and()
             .sessionManagement()
             .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             .and()
             .authenticationProvider(authenticationProvider)
             .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()

    }
}