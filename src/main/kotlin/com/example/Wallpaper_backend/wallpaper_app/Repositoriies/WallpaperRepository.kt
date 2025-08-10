package com.example.Wallpaper_backend.wallpaper_app.Repositoriies

import com.example.Wallpaper_backend.wallpaper_app.Entity.Wallpaper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.springframework.data.jpa.repository.JpaRepository


interface WallpaperRepository : JpaRepository<Wallpaper, Long> {
    fun findByCategoryId(categoryId: Long, pageable: Pageable): Page<Wallpaper>
}