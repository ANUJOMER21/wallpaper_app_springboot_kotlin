package com.example.Wallpaper_backend.wallpaper_app.Controllers

import com.example.Wallpaper_backend.wallpaper_app.Dto.LoginResponseDto
import com.example.Wallpaper_backend.wallpaper_app.Dto.UserDto
import com.example.Wallpaper_backend.wallpaper_app.Services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {
    @PostMapping("/register")
    fun register(@Valid @RequestBody userDto: UserDto): ResponseEntity<String> {
        userService.registerUser(userDto)
        return ResponseEntity.ok("User registered")
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody userDto: UserDto): ResponseEntity<LoginResponseDto> {
        return ResponseEntity.ok(userService.loginUser(userDto))
    }
}