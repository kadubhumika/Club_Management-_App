package com.example.club_mangement_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.On_BoardingScreen
import com.example.club_mangement_app.ui.LoginScreen
import com.example.club_mangement_app.ui.SignupScreen
import com.example.club_mangement_app.SplashScreen
import com.example.club_mangement_app.admin.AdminDashboardScreen
import com.example.club_mangement_app.authentication.utils.SharedPrefManager


@Composable
fun AppNavHost(sharedPrefManager: SharedPrefManager) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("onboarding") {
            On_BoardingScreen(navController = navController)
        }

        composable("login") {
            LoginScreen(
                sharedPrefManager = sharedPrefManager,
                onLoginSuccess = { userRole ->
                    if (userRole == "Admin") {
                        navController.navigate("admin_dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                },
                onSignupClick = {
                    navController.navigate("signup")
                }
            )
        }

        composable("signup") {
            SignupScreen(sharedPrefManager = sharedPrefManager) {
                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        }

        composable("admin_dashboard") {
            AdminDashboardScreen(navController = navController)
        }
    }
}
