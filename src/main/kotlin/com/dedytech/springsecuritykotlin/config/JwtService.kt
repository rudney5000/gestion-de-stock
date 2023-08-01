package com.dedytech.springsecuritykotlin.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import kotlin.collections.HashMap

@Service
class JwtService {

    val SECRET_KEY = "XFHXyPwajNFxK3cszk+mQy3nCLDfxaxtnpIoKonGfMLFhSYJhtrwg04JKbbOIYKf"
    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    fun generateToken(userDetails: UserDetails) = generateToken(HashMap(), userDetails)

    fun generateToken(
        extraClaims: Map<String, Object>,
        userDetails: UserDetails
    ): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.ES256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
         val username: String = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    fun isTokenExpired(token: String): Boolean = extractExpiration(token).before(Date())

    fun extractExpiration(token: String): Date = extractClaim(token, Claims::getExpiration)

    fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}

