package com.example.club_mangement_app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AppBottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController
) {
    val items = listOf("Dashboard", "Tasks", "Chat", "Profile", "Settings")
    val icons = listOf(
        Icons.Default.Dashboard,
        Icons.Default.Task,
        Icons.Default.Chat,
        Icons.Default.Person,
        Icons.Default.Settings
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    onItemSelected(index)
                    when (index) {
                        0 -> navController.navigate("dashboard") { launchSingleTop = true }
                        1 -> navController.navigate("tasks") { launchSingleTop = true }
                        2 -> navController.navigate("chat") { launchSingleTop = true }
                        3 -> navController.navigate("edit_profile") { launchSingleTop = true }
                        4 -> navController.navigate("settings") { launchSingleTop = true }
                    }
                }
            )
        }
    }
}
