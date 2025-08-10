package com.example.Wallpaper_backend.wallpaper_app.Repositoriies

import com.example.Wallpaper_backend.wallpaper_app.Entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
    fun findByName(name : String) : Category?
}