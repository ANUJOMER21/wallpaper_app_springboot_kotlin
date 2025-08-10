package com.example.Wallpaper_backend.wallpaper_app.Dto

import jakarta.validation.constraints.NotBlank

data class CategoryDto(
    @field:NotBlank(message = "Name is required")
    val name: String,
    val description: String? = null
)