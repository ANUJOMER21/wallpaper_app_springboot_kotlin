package com.example.Wallpaper_backend.wallpaper_app.Security


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        val header = req.getHeader("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)
            val username = jwtUtil.extractUsername(token)
            if (username != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
                if (jwtUtil.isTokenValid(token, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(req)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }
        chain.doFilter(req, res)
    }
}