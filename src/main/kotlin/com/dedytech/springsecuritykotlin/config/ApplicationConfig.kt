package com.dedytech.springsecuritykotlin.config

import com.dedytech.springsecuritykotlin.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Configuration
class ApplicationConfig(
    @Autowired
    private var repository: UserRepository
):UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByEmail(username)
            .orElseThrow { UsernameNotFoundException("User not found") }
        return user.toUserDetails()
    }
}
