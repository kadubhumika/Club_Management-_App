package com.example.club_mangement_app


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.navigation.NavController
import com.example.club_mangement_app.R

@Composable
fun SplashScreen(navController: NavController) {

    LaunchedEffect(Unit) {
        delay(2000)  // 2 seconds
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background( color = Color.Black)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
                .background(color = Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.outline_crowdsource_24),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(150.dp)
            )
        }
    }
}
