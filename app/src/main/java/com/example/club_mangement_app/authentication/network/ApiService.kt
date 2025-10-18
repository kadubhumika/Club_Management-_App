package com.example.club_mangement_app.authentication.network

import com.example.club_mangement_app.authentication.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class SignupRequest(
    val name : String,
    val email : String,
    val password : String,
    val role : String,
    val domain : String
)

data class LoginRequest(
    val email: String,
    val password: String,
    val role: String,
    val domain: String
)

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val user: T? = null
)

interface ApiService {
    @POST("bhumika/signup.php")
    suspend fun signup(@Body request: SignupRequest): Response<ApiResponse<User>>

    @POST("bhumika/login.php")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<User>>


}