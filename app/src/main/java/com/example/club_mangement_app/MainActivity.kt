package com.example.club_mangement_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.example.club_mangement_app.ui.theme.Club_Mangement_AppTheme
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import com.example.club_mangement_app.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPrefManager = SharedPrefManager(this)

        setContent {
            Club_Mangement_AppTheme {
                AppNavHost(sharedPrefManager = sharedPrefManager)
            }
        }
    }
}
