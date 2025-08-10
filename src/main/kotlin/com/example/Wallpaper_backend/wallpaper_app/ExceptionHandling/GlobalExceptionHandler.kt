package com.example.Wallpaper_backend.wallpaper_app.ExceptionHandling



import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.multipart.MaxUploadSizeExceededException
import jakarta.validation.ConstraintViolationException

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidation(e: ConstraintViolationException): ResponseEntity<String> {
        return ResponseEntity(e.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleFileSizeLimit(e: MaxUploadSizeExceededException): ResponseEntity<String> {
        return ResponseEntity("File size exceeds limit", HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(e: Exception): ResponseEntity<String> {
        return ResponseEntity("An error occurred: ${e.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}