package com.example.club_mangement_app.authentication.model

data class User(
    var id : Int,
    var name : String,
    var email : String,
    var role : String,
    var domain : String,
    var profile_img : String?
)