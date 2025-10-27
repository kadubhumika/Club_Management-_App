package com.example.club_mangement_app.authentication.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.club_mangement_app.authentication.utils.SharedPrefManager

@Composable
fun SettingsScreen(navController: NavController, sharedPrefManager: SharedPrefManager) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                sharedPrefManager.clearUser()
                navController.navigate("login") {
                    popUpTo("settings") { inclusive = true }
                    launchSingleTop = true
                }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}