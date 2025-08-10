Wallpaper App Backend
A robust Spring Boot backend for a wallpaper application, built with Kotlin and PostgreSQL. This project supports user authentication (email/password and Google OAuth2), wallpaper categories, and local image storage. It follows best practices with a layered architecture, JWT-based security, input validation, and error handling.
Features

User Authentication:
Register and login with email/password.
Google OAuth2 sign-in with JWT token generation.


Categories: Create and retrieve wallpaper categories with pagination.
Wallpapers: Upload images to a local directory, associate with categories, and retrieve paginated lists.
Security: JWT-based stateless authentication, with protected endpoints.
File Storage: Images stored locally in an uploads/ directory, served via HTTP.
RESTful APIs: Clean, documented endpoints with validation and error handling.
Scalability: Uses Spring Data JPA for database operations and pagination for efficient data retrieval.

Technologies

Spring Boot: 3.3.4
Kotlin: 1.9.24
PostgreSQL: 16 (or compatible version)
JWT: For secure authentication
Dependencies: Spring Web, Spring Security, OAuth2 Client, Spring Data JPA, PostgreSQL Driver, Validation, JJWT

Prerequisites

JDK 17+: Download
PostgreSQL 16: Installed and running, with a wallpaper_db database created.
IntelliJ IDEA: Recommended for development (Community Edition is free).
Google OAuth2 Credentials: Client ID and Secret from Google Developer Console.
Gradle: Included via Gradle Wrapper (gradlew).

Installation

Clone the Repository:
git clone <repository-url>
cd wallpaper_app


Set Up PostgreSQL:

Ensure PostgreSQL is installed and running:brew services start postgresql@16


Create the database:psql -U postgres
CREATE DATABASE wallpaper_db;
\q


Verify connection:psql -U postgres -d wallpaper_db




Create Uploads Directory:
mkdir uploads
chmod 775 uploads


Configure Application:

Edit src/main/resources/application.yml:
Replace yourpassword with your PostgreSQL password.
Add Google OAuth2 client-id and client-secret.
Generate a JWT secret:openssl rand -hex 32

Update jwt.secret with the generated value.




Build and Run:
./gradlew clean build
./gradlew bootRun


The app runs on http://localhost:8080.
Check logs for "Tomcat started on port(s): 8080".



API Documentation
Authentication

POST /auth/register
Request: {"email": "test@example.com", "password": "pass"}
Response: "User registered"


POST /auth/login
Request: {"email": "test@example.com", "password": "pass"}
Response: {"token": "<jwt-token>"}


Google OAuth2:
Access: http://localhost:8080/login/oauth2/code/google
Redirects to Google login, returns {"token": "<jwt-token>"} on success.



Categories

POST /categories (requires JWT)
Request: {"name": "Nature", "description": "Nature wallpapers"}
Response: "Category created"


GET /categories?page=0&size=10 (requires JWT)
Response: Paginated list of categories.



Wallpapers

POST /wallpapers (requires JWT)
Request (form-data): file (image file), title (optional), categoryId (optional)
Example:curl -X POST http://localhost:8080/wallpapers -H "Authorization: Bearer <token>" -F "file=@image.jpg" -F "title=Sunset" -F "categoryId=1"


Response: {"id": 1, "imagePath": "<uuid>_image.jpg", "title": "Sunset", "categoryId": 1}


GET /wallpapers/category/{categoryId}?page=0&size=10 (requires JWT)
Response: Paginated list of wallpapers.


GET /wallpapers/image/{fileName} (public)
Response: Serves the image file.



Authentication Header
For protected endpoints, include:
Authorization: Bearer <jwt-token>

Project Structure
wallpaper_app
├── src
│   ├── main
│   │   ├── kotlin/com/example/wallpaperapp
│   │   │   ├── config/CorsConfig.kt
│   │   │   ├── controller/{Auth,Category,Wallpaper}Controller.kt
│   │   │   ├── dto/{User,Category,Wallpaper}Dto.kt
│   │   │   ├── entity/{User,Category,Wallpaper}.kt
│   │   │   ├── exception/GlobalExceptionHandler.kt
│   │   │   ├── repository/{User,Category,Wallpaper}Repository.kt
│   │   │   ├── security/{JwtUtil,JwtAuthenticationFilter,CustomUserDetailsService,SecurityConfig}.kt
│   │   │   ├── service/{User,Category,Wallpaper}Service.kt
│   │   │   └── WallpaperAppApplication.kt
│   │   └── resources
│   │       └── application.yml
├── uploads/  # Local image storage
└── build.gradle.kts

Troubleshooting

Database Errors:
Ensure PostgreSQL is running: brew services list.
Verify application.yml credentials match your setup.


File Upload Issues:
Confirm uploads/ exists and is writable.
Check file size (max 10MB).


Build Errors:
Run ./gradlew clean build.
Ensure package names match com.example.wallpaperapp.


Debug Logs:Add to application.yml:logging:
level:
org.springframework: DEBUG
com.example: DEBUG



Deployment
For production:

Use environment variables for secrets:spring:
datasource:
password: ${DB_PASSWORD}


Set spring.jpa.hibernate.ddl-auto=validate.
Use Flyway/Liquibase for database migrations.
Secure uploads/ access (e.g., move outside public directory).
Deploy with Docker or a platform like Heroku/AWS.

Contributing

Fork the repository.
Create a feature branch: git checkout -b feature-name.
Commit changes: git commit -m "Add feature".
Push to the branch: git push origin feature-name.
Open a pull request.

License
MIT License. See LICENSE for details.