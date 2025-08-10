package com.example.Wallpaper_backend.wallpaper_app.Controllers

import com.example.Wallpaper_backend.wallpaper_app.Dto.CategoryDto
import com.example.Wallpaper_backend.wallpaper_app.Entity.Category
import com.example.Wallpaper_backend.wallpaper_app.Services.CategoryService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {
    @PostMapping
    fun create(@Valid @RequestBody categoryDto: CategoryDto): ResponseEntity<String> {
        categoryService.create(categoryDto)
        return ResponseEntity.ok("Category created")
    }

    @GetMapping
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<Category>> {
        return ResponseEntity.ok(categoryService.getAll(PageRequest.of(page, size)))
    }
}