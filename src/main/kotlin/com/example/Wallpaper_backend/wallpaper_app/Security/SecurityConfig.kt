package com.example.Wallpaper_backend.wallpaper_app.Security



import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: CustomUserDetailsService
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager = config.authenticationManager

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { }  // Enable CORS (configure in application.yml or a CorsConfig class)
            .authorizeHttpRequests {
                it.requestMatchers("/auth/**", "/uploads/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login {
                it.successHandler { req, res, auth ->
                    val user = userDetailsService.loadUserByUsername(auth.name)
                    val token = jwtUtil.generateToken(user)
                    res.writer.write("{\"token\": \"$token\"}")
                }
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(JwtAuthenticationFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}