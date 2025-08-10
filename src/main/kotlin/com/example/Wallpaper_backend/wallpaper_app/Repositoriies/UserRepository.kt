package com.example.Wallpaper_backend.wallpaper_app.Repositoriies

import com.example.Wallpaper_backend.wallpaper_app.Entity.User
import com.example.Wallpaper_backend.wallpaper_app.Entity.Wallpaper
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String):User?
    fun findByGoogleId(googleId: String): User?
}