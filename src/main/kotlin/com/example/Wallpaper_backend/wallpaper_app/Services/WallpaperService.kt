package com.example.Wallpaper_backend.wallpaper_app.Services



import com.example.Wallpaper_backend.wallpaper_app.Dto.WallpaperDto
import com.example.Wallpaper_backend.wallpaper_app.Entity.Wallpaper
import com.example.Wallpaper_backend.wallpaper_app.Repositoriies.CategoryRepository
import com.example.Wallpaper_backend.wallpaper_app.Repositoriies.WallpaperRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID
import org.springframework.beans.factory.annotation.Value

@Service
class WallpaperService(
    private val wallpaperRepository: WallpaperRepository,
    private val categoryRepository: CategoryRepository,
    @Value("\${app.upload-dir}") private val uploadDir: String
) {
    fun uploadWallpaper(file: MultipartFile, title: String?, categoryId: Long?): WallpaperDto {
        val category = categoryId?.let { categoryRepository.findById(it).orElseThrow { IllegalArgumentException("Category not found") } }
        val fileName = "${UUID.randomUUID()}_${file.originalFilename}"
        val filePath = Paths.get(uploadDir, fileName)
        Files.createDirectories(filePath.parent)
        Files.write(filePath, file.bytes)
        val wallpaper = Wallpaper(imagePath = fileName, title = title, category = category)
        val saved = wallpaperRepository.save(wallpaper)
        return WallpaperDto(saved.id, saved.imagePath, saved.title, saved.category?.id)
    }

    fun getByCategory(categoryId: Long, pageable: Pageable): Page<WallpaperDto> {
        return wallpaperRepository.findByCategoryId(categoryId, pageable).map {
            WallpaperDto(it.id, it.imagePath, it.title, it.category?.id)
        }
    }

    fun getImagePath(fileName: String): Path = Paths.get(uploadDir, fileName)
}