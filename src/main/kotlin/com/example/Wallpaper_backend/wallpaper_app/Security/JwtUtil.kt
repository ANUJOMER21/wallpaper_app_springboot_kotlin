package com.example.Wallpaper_backend.wallpaper_app.Security


import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.expiration}") private val expiration: Long
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(userDetails: UserDetails): String {
        return Jwts.builder()
            .setSubject(userDetails.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact()
    }

    fun extractUsername(token: String): String? {
        return getClaims(token)?.subject
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean = getClaims(token)?.expiration?.before(Date()) ?: true

    private fun getClaims(token: String): Claims? {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        } catch (e: Exception) {
            null
        }
    }
}