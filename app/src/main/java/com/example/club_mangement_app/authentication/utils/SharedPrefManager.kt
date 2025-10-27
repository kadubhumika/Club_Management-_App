package com.example.club_mangement_app.authentication.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.club_mangement_app.authentication.model.User
import com.google.gson.Gson

class SharedPrefManager (context : Context){

    private val prefs : SharedPreferences = context.getSharedPreferences("club_prefs", Context.MODE_PRIVATE)

    fun saveUser(user : User){
        val json = Gson().toJson(user)
        prefs.edit().putString("user_data",json).apply()


    }
    fun getUser():User?{
        val json = prefs.getString("user_data",null)
        return json?.let { Gson().fromJson(it, User::class.java) }

    }
    fun clear(){
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = getUser() != null

    fun clearUser() { clear() }

    fun hasSeenOnboarding(): Boolean {
        return prefs.getBoolean("seen_onboarding", false)
    }

    fun setOnboardingSeen() {
        prefs.edit().putBoolean("seen_onboarding", true).apply()
    }


}

