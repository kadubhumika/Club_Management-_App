package com.example.club_mangement_app.navigation

import CoordinatorScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.EditProfileScreen
import com.example.club_mangement_app.On_BoardingScreen
import com.example.club_mangement_app.ui.LoginScreen
import com.example.club_mangement_app.ui.SignupScreen
import com.example.club_mangement_app.SplashScreen
import com.example.club_mangement_app.admin.AdminDashboardScreen
import com.example.club_mangement_app.settings.SettingsScreen
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import com.example.club_mangement_app.chat.ChatScreen
import com.example.club_mangement_app.contributionMap.ProfileScreen
import com.example.club_mangement_app.coordinator.App_Coordinator_Screen

import com.example.club_mangement_app.coordinator.Dsa_Coordinator_Screen
import com.example.club_mangement_app.coordinator.Graphics_Coordinator_Screen
import com.example.club_mangement_app.coordinator.MemberDashboardScreen
import com.example.club_mangement_app.coordinator.Web_Coordinator_Screen
import com.example.club_mangement_app.info.ClubInformationPage
import com.example.club_mangement_app.notification.NotificationScreen
import com.example.club_mangement_app.settings.SettingsScreen


@Composable
fun AppNavHost(sharedPrefManager: SharedPrefManager) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            SplashScreen(navController = navController)
        }

        composable("tasks") {
            CoordinatorScreen(navController = navController)
        }


        composable("onboarding") {
            On_BoardingScreen(navController = navController)
        }

        composable("edit_profile") {
            EditProfileScreen(navController = navController)

        }

        composable("chat") {
            ChatScreen(navController = navController)
        }



        composable("settings") {
            val context = LocalContext.current
            val sharedPrefManager = SharedPrefManager(context)
            SettingsScreen(
                onNavigate = {},
                navController = navController
            )
        }

        composable("profile") {
            ProfileScreen(
                onNavigate = {},
                navController = navController
            )
        }





        composable("login") {
            LoginScreen(
                sharedPrefManager = sharedPrefManager,
                onLoginSuccess = { userRole ->
                    val user = sharedPrefManager.getUser()
                    when (userRole) {
                        "Admin" -> navController.navigate("admin_dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                        "Coordinator" -> {
                            when (user?.domain) {
                                "Web" -> navController.navigate("web_coordinator_dashboard") { popUpTo("login") { inclusive = true } }
                                "Apps" -> navController.navigate("app_coordinator_dashboard") { popUpTo("login") { inclusive = true } }
                                "Graphics" -> navController.navigate("graphics_coordinator_dashboard") { popUpTo("login") { inclusive = true } }
                                "DSA" -> navController.navigate("dsa_coordinator_dashboard") {
                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }

                                else -> navController.navigate("member_dashboard") { popUpTo("login") { inclusive = true } }
                            }
                        }
                        "Member" -> navController.navigate("member_dashboard") { popUpTo("login") { inclusive = true } }
                        else -> navController.navigate("login")
                    }
                },
                onSignupClick = {
                    navController.navigate("signup")
                },

                navController = navController
            )
        }

        composable("signup") {
            SignupScreen(navController = navController,sharedPrefManager = sharedPrefManager) {

                val user = sharedPrefManager.getUser()
                if (user?.role == "Admin") {
                    navController.navigate("admin_dashboard") {
                        popUpTo("signup") { inclusive = true }
                    }
                } else {
                    navController.navigate("member_dashboard") {
                        popUpTo("signup") { inclusive = true }
                    }
                }
            }
        }

        composable("admin_dashboard") {
            AdminDashboardScreen(navController = navController)
        }

        composable("member_dashboard") {
            MemberDashboardScreen(navController = navController)
        }

        composable("club_info") {
            ClubInformationPage(navController = navController)
        }


        composable("dsa_coordinator_dashboard") { Dsa_Coordinator_Screen(navController) }

        composable("app_coordinator_dashboard") { App_Coordinator_Screen(navController) }

        composable("graphics_coordinator_dashboard") { Graphics_Coordinator_Screen(navController) }


        composable("web_coordinator_dashboard") { Web_Coordinator_Screen(navController) }

        composable("notifications") {
            NotificationScreen(navController = navController)
        }



    }
}
