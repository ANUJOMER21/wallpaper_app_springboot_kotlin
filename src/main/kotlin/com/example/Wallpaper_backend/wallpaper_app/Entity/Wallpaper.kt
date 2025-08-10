package com.example.Wallpaper_backend.wallpaper_app.Entity

import jakarta.persistence.*

@Entity
@Table(name = "wallpapers")
class Wallpaper(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "image_path")
    val imagePath: String,  // Local filesystem path (e.g., uploads/image.jpg)
    val title: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    val category: Category? = null
) {
}