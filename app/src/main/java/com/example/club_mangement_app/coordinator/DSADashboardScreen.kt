package com.example.club_mangement_app.coordinator


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.example.club_mangement_app.components.*

@Composable
fun DSADashboardScreen(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedBottomNavItem by remember { mutableStateOf(0) }


    val dsaTasks = listOf(
        TaskData("Organize Workshop on Data Structures", "John Doe", "2023-10-26", "In Progress", Color(0xFFFFA726)),
        TaskData("Prepare Quiz for Algorithms", "Jane Smith", "2023-10-28", "Pending", Color(0xFF64B5F6)),
        TaskData("Finalize Speaker for Tech Talk", "Mike Johnson", "2023-10-30", "Done", Color(0xFF66BB6A))
    )

    Scaffold(
        topBar = { AppTopBar(title = "DSA Coordinator Dashboard") },
        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index -> selectedBottomNavItem = index }
            )
        },
        floatingActionButton = {
            AddAssignmentFAB(onClick = { navController.navigate("assignTaskRoute") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            StatusTabRow(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )


            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {
                    Text(
                        text = "DSA Domain Dashboard",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(dsaTasks.size) { index ->
                    TaskRowItem(data = dsaTasks[index])
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDSADashboardScreen() {
    DSADashboardScreen(rememberNavController())
}