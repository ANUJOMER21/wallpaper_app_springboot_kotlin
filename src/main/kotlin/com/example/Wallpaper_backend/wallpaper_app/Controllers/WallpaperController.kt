package com.example.Wallpaper_backend.wallpaper_app.Controllers


import com.example.Wallpaper_backend.wallpaper_app.Dto.WallpaperDto
import com.example.Wallpaper_backend.wallpaper_app.Services.WallpaperService
import org.springframework.core.io.PathResource
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/wallpapers")
class WallpaperController(private val wallpaperService: WallpaperService) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("title") title: String?,
        @RequestPart("categoryId") categoryId: Long?
    ): ResponseEntity<WallpaperDto> {
        return ResponseEntity.ok(wallpaperService.uploadWallpaper(file, title, categoryId))
    }

    @GetMapping("/category/{categoryId}")
    fun getByCategory(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<WallpaperDto>> {
        return ResponseEntity.ok(wallpaperService.getByCategory(categoryId, PageRequest.of(page, size)))
    }

    @GetMapping("/image/{fileName}")
    fun getImage(@PathVariable fileName: String): ResponseEntity<PathResource> {
        val path = wallpaperService.getImagePath(fileName)
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG) // Adjust based on your image types
            .body(PathResource(path))
    }
}