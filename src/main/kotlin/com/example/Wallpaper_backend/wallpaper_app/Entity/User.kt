package com.example.Wallpaper_backend.wallpaper_app.Entity


import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(unique = true)
    val email: String,
    @Column(name = "hashed_password")  // Renamed to avoid clash
    val hashedPassword: String? = null,  // Null for OAuth users
    val googleId: String? = null,
    val role: String = "USER"
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = listOf()
    override fun getPassword(): String? = hashedPassword
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}