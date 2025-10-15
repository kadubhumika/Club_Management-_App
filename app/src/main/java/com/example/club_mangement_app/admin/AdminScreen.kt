package com.example.club_mangement_app.admin




import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.club_mangement_app.R

import com.example.club_mangement_app.components.ProjectData
import com.example.club_mangement_app.components.ProjectTile
import com.example.club_mangement_app.components.StatCard
import com.example.club_mangement_app.ui.theme.BlueStatus
import com.example.club_mangement_app.ui.theme.Club_Mangement_AppTheme
import com.example.club_mangement_app.ui.theme.EmeraldSecondary
import com.example.club_mangement_app.ui.theme.IndigoPrimary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Admin Dashboard") }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Overall Status",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))

            StatCard(
                title = "Total Members",
                value = 74,
                icon = painterResource(id = R.drawable.outline_groups_2_24),
                iconBackgroundColor = IndigoPrimary
            )

            StatCard(
                title = "Active Coordinators",
                value = 9,
                icon = painterResource(id = R.drawable.active_coordinator),
                iconBackgroundColor = EmeraldSecondary
            )

            StatCard(
                title = "Active Projects",
                value = 3,
                icon = painterResource(id = R.drawable.outline_task_24),
                iconBackgroundColor = BlueStatus
            )


            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Domain Projects",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))


            val projects = listOf(
                ProjectData(
                    domain = "App Development",
                    name = "EcoTrack",
                    color = EmeraldSecondary,
                    activeSprints = 2,
                    members = 24,
                    coordinators = 3
                ),
                ProjectData(
                    domain = "Web Development",
                    name = "ClubPortal",
                    color = Color(0xFFFF5722),
                    activeSprints = 1,
                    members = 18,
                    coordinators = 2
                ),
                ProjectData(
                    domain = "Data Structures & Algos",
                    name = "AlgoMaster",
                    color = IndigoPrimary,
                    activeSprints = 2,
                    members = 18,
                    coordinators = 4
                ),
                ProjectData(
                    domain = "Graphics & Design",
                    name = "Blender Project",
                    color = Color(0xFFE040FB),
                    activeSprints = 2,
                    members = 18,
                    coordinators = 4
                )
            )

            projects.forEach { project ->
                ProjectTile(project = project)
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