package com.example.Wallpaper_backend.wallpaper_app.Services

import com.example.Wallpaper_backend.wallpaper_app.Dto.LoginResponseDto
import com.example.Wallpaper_backend.wallpaper_app.Dto.UserDto
import com.example.Wallpaper_backend.wallpaper_app.Entity.User
import com.example.Wallpaper_backend.wallpaper_app.Repositoriies.UserRepository
import com.example.Wallpaper_backend.wallpaper_app.Security.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {

    fun registerUser(user: UserDto): User {
        if(userRepository.findByEmail(user.email) != null) throw IllegalStateException("User already registered")
        val user =User(
            email = user.email,
            hashedPassword = user.password?.let { passwordEncoder.encode(it) },
        )
        return userRepository.save(user)
    }

    fun loginUser(user: UserDto): LoginResponseDto {
        val auth=authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user.email,user.password)
        )
        val token=jwtUtil.generateToken(auth.principal as User)
        return LoginResponseDto(token)
    }

    fun registerOrGetGoogleUser(email: String, googleId: String): User {
        var user=userRepository.findByGoogleId(googleId)
        if(user==null){
            user=userRepository.findByEmail(email)?: User(email = email,googleId = googleId)
            user=userRepository.save(user)
        }
        return user
    }
}