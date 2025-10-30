package com.example.club_mangement_app

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    val scaleAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.6f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = ""
    )

    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2500)

        val sharedPrefManager = SharedPrefManager(navController.context)
        val user = sharedPrefManager.getUser()
        val hasSeenOnboarding = sharedPrefManager.hasSeenOnboarding()

        when {
            !hasSeenOnboarding -> {
                navController.navigate("onboarding") {
                    popUpTo("splash") { inclusive = true }
                }
            }

            user != null -> {
                when (user.role) {
                    "Admin" -> navController.navigate("admin_dashboard") {
                        popUpTo("splash") { inclusive = true }
                    }

                    "Coordinator" -> {
                        when (user.domain) {
                            "Web" -> navController.navigate("web_coordinator_dashboard") {
                                popUpTo("splash") { inclusive = true }
                            }
                            "Apps" -> navController.navigate("app_coordinator_dashboard") {
                                popUpTo("splash") { inclusive = true }
                            }
                            "Graphics" -> navController.navigate("graphics_coordinator_dashboard") {
                                popUpTo("splash") { inclusive = true }
                            }
                            "DSA" -> navController.navigate("dsa_coordinator_dashboard") {
                                popUpTo("splash") { inclusive = true }
                            }
                            else -> navController.navigate("member_dashboard") {
                                popUpTo("splash") { inclusive = true }
                            }
                        }
                    }

                    else -> navController.navigate("member_dashboard") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }

            else -> {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }


    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFB8B8D1),
            Color(0xFF7BA5DD),
            Color(0xFF9BA5D6),
            Color(0xFFB8B8C8)
        )
    )


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        color = Color.Transparent
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Image(
                    painter = painterResource(id = R.drawable.outline_crowdsource_24),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(180.dp)
                        .scale(scaleAnim)
                        .alpha(alphaAnim)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Club Management App",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.alpha(alphaAnim)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}
