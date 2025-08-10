package com.example.Wallpaper_backend.wallpaper_app.Services


import com.example.Wallpaper_backend.wallpaper_app.Dto.CategoryDto
import com.example.Wallpaper_backend.wallpaper_app.Entity.Category
import com.example.Wallpaper_backend.wallpaper_app.Repositoriies.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun create(categoryDto: CategoryDto): Category {
        if (categoryRepository.findByName(categoryDto.name) != null) throw IllegalArgumentException("Category exists")
        return categoryRepository.save(Category(name = categoryDto.name, description = categoryDto.description))
    }

    fun getAll(pageable: Pageable): Page<Category> = categoryRepository.findAll(pageable)
}