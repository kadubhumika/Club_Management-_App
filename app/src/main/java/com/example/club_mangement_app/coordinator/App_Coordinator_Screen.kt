package com.example.club_mangement_app.coordinator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.components.AppBottomNavBar
import com.example.club_mangement_app.components.AppTopBar

// -------------------- DATA MODELS --------------------

data class AppTask(
    val title: String,
    val status: AppTaskStatus,
    val assignee: String,
    val assigneeAvatar: String,
    val deadline: String
)

enum class AppTaskStatus(val displayName: String, val color: Color, val bgColor: Color) {
    PENDING("Pending", Color(0xFFEF5350), Color(0x33EF5350)),
    IN_PROGRESS("In Progress", Color(0xFFFFB74D), Color(0x33FFB74D)),
    DONE("Done", Color(0xFF66BB6A), Color(0x3366BB6A))
}

data class AppBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

// -------------------- MAIN COMPOSABLE --------------------

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App_Coordinator_Screen() {
    val navController = rememberNavController()
    var selectedBottomNavItem by remember { mutableStateOf(0) }
    val selectedFilter = remember { mutableStateOf("All") }

    val tasks = listOf(
        AppTask(
            title = "Implement user authentication",
            status = AppTaskStatus.PENDING,
            assignee = "Alex",
            assigneeAvatar = "https://example.com/avatar1.jpg",
            deadline = "2023-11-10"
        ),
        AppTask(
            title = "Fix navigation bugs in main screen",
            status = AppTaskStatus.IN_PROGRESS,
            assignee = "Maria",
            assigneeAvatar = "https://example.com/avatar2.jpg",
            deadline = "2023-11-15"
        ),
        AppTask(
            title = "Design new onboarding flow",
            status = AppTaskStatus.DONE,
            assignee = "Chris",
            assigneeAvatar = "https://example.com/avatar3.jpg",
            deadline = "2023-11-05"
        ),
        AppTask(
            title = "Optimize app performance",
            status = AppTaskStatus.IN_PROGRESS,
            assignee = "John",
            assigneeAvatar = "https://example.com/avatar4.jpg",
            deadline = "2023-11-20"
        )
    )

    Scaffold(
        topBar = {
            AppTopBar(title = "Admin Dashboard", navController = navController)
        },
        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index -> selectedBottomNavItem = index },
                navController = navController
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle add task */ },
                containerColor = Color(0xFF42A5F5),
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF1F3F4))
        ) {
            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("All", "Pending", "In Progress", "Done").forEach { filter ->
                    AppFilterChip(
                        text = filter,
                        isSelected = filter == selectedFilter.value,
                        onClick = { selectedFilter.value = filter }
                    )
                }
            }

            // Task List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks) { task ->
                    AppTaskCard(task = task)
                }
            }
        }
    }
}

// -------------------- COMPONENTS --------------------

@Composable
fun AppFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF42A5F5) else Color(0xFFE8EAED)
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF42A5F5) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun AppTaskCard(task: AppTask) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF42A5F5))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Assignee",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }

            // Task Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Text(
                    text = "Deadline: ${task.deadline}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Status Chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(task.status.bgColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = task.status.displayName,
                    color = task.status.color,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun App_Coordinator_ScreenPreview() {
    MaterialTheme {
        App_Coordinator_Screen()
    }
}
