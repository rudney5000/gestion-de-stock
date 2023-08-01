package com.dedytech.springsecuritykotlin.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: AuthenticationManager,
    private val userDetailsService: UserDetailsService
): OncePerRequestFilter(

) {
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        TODO("Not yet implemented")
        val userEmail: String
        val authHeader = request.getHeader("Authorisation")
        if (authHeader == null ||!authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response)
            return
        }
        val jwt = authHeader.substring(7).toString()
        userEmail = jwtService.extractUsername(jwt) // todo extract the userEmail from JWt token
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            // l'utilisateur n'est pas authentifié
            val userDetails: UserDetails = this.userDetailsService.loadUserByUsername(userEmail)
            if (jwtService.isTokenValid(jwt, userDetails)){
                val authToken: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                )
                authToken.setDetails(
                    WebAuthenticationDetailsSource().buildDetails(request)
                )
                SecurityContextHolder.getContext().setAuthentication(authToken)
            }
        }
        filterChain.doFilter(request, response)
    }

}