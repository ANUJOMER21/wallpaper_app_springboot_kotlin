package com.example.Wallpaper_backend.wallpaper_app.Entity
import jakarta.persistence.*
import org.hibernate.engine.internal.Cascade

@Entity
@Table(name = "category")
class Category (
   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
val id: Long = 0,
@Column(unique = true)
val name: String,
val description: String? = null,
@OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
val wallpapers: List<Wallpaper> = emptyList()
)
{
}