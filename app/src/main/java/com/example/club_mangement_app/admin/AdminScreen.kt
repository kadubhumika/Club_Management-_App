package com.example.club_mangement_app.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Import ALL components and data classes from the shared package
import com.example.club_mangement_app.components.AdminProjectCard
import com.example.club_mangement_app.components.AppBottomNavBar
import com.example.club_mangement_app.components.AppTopBar
import com.example.club_mangement_app.components.MetricBox
import com.example.club_mangement_app.components.ProjectData
import com.example.club_mangement_app.ui.theme.Club_Mangement_AppTheme

@Composable
fun AdminDashboardScreen(navController: NavController) {
    // State for bottom navigation (Dashboard is typically index 0)
    var selectedBottomNavItem by remember { mutableStateOf(0) }

    // Dummy data structured to fit the ProjectData class defined in components
    val projectDataList = listOf(
        ProjectData("DSA", "Active", 1, 1),
        ProjectData("Web", "Active", 3, 3),
        ProjectData("App", "Active", 2, 2),
        ProjectData("Graphics", "Inactive", 0, 0, Color(0xFFE57373)) // Red for Inactive
    )

    Scaffold(
        topBar = {
            AppTopBar(title = "Admin Dashboard")
        },
        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index ->
                    selectedBottomNavItem = index
                    // Navigation logic would go here
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // --- Overall Status (Metrics) ---
            Text(
                text = "Overall Status",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            MetricBox(title = "Total Members", value = "74")
            MetricBox(title = "Total Coordinators", value = "9")
            MetricBox(title = "Pending Tasks", value = "12")


            Spacer(modifier = Modifier.height(24.dp))

            // --- Domain Projects ---
            Text(
                text = "Domain Projects",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            projectDataList.forEach { project ->
                AdminProjectCard(data = project)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    Club_Mangement_AppTheme {
        AdminDashboardScreen(navController = rememberNavController())
    }
}