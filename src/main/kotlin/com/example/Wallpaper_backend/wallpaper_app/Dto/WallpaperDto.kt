package com.example.Wallpaper_backend.wallpaper_app.Dto

data class WallpaperDto(
    val id: Long,
    val imagePath: String,
    val title: String?,
    val categoryId: Long?
)
