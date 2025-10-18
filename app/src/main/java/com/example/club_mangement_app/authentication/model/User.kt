package com.example.club_mangement_app.authentication.model

data class User(
    val id : Int,
    val name : String,
    val email : String,
    val role : String,
    val domain : String
)