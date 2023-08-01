package com.dedytech.springsecuritykotlin.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Autowired
    private var jwtAuthFilter: JwtAuthenticationFilter,
//    private val authenticationProvider: AuthenticationProvider
): WebSecurityConfiguration() {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        lateinit var authenticationProvider: AuthenticationProvider
//
//        lateinit var jwtAuthFilter: JwtAuthenticationFilter
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