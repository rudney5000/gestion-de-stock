package com.dedytech.springsecuritykotlin.auth

import com.dedytech.springsecuritykotlin.config.JwtService
import com.dedytech.springsecuritykotlin.user.Role
import com.dedytech.springsecuritykotlin.user.User
import com.dedytech.springsecuritykotlin.user.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {
    fun register(request: RegisterRequest): AuthenticationResponse {
        TODO("Not yet implemented")
        var user = User.builder()
            .firstname(request.firstname)
            .lastname(request.lastname)
            .email(request.email)
            .password(passwordEncoder.encode(request.password))
            .role(Role.USER)
            .build()
        userRepository.save(user)
        var jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build()
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse? {
        TODO("Not yet implemented")

    }
}