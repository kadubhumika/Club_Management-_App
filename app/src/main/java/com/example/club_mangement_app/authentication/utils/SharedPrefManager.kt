package com.example.club_mangement_app.authentication.utils

import android.adservices.ondevicepersonalization.UserData
import android.content.Context
import android.content.SharedPreferences
import com.example.club_mangement_app.authentication.model.User
import com.google.gson.Gson

class SharedPrefManager (context : Context){

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("UserPref", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        val json = Gson().toJson(user)
        sharedPref.edit().putString("user_data", json).apply()
    }
    fun resetOnboarding() {
        sharedPref.edit().putBoolean("seen_onboarding", false).apply()
    }


    fun getUser(): User? {
        val json = sharedPref.getString("user_data", null)
        return json?.let { Gson().fromJson(it, User::class.java) }
    }

    fun clearUser() {
        sharedPref.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = getUser() != null



    fun logout() {
        sharedPref.edit().clear().apply()
    }


    fun hasSeenOnboarding(): Boolean {
        return sharedPref.getBoolean("seen_onboarding", false)
    }

    fun setOnboardingSeen() {
        sharedPref.edit().putBoolean("seen_onboarding", true).apply()
    }


}

