package com.example.club_mangement_app.coordinator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.components.AppBottomNavBar
import com.example.club_mangement_app.components.AppTopBar
import com.example.club_mangement_app.components.StatusTabRow

// ------------------- Data Models -------------------

data class Task(
    val title: String,
    val status: TaskStatus,
    val assignee: String,
    val deadline: String
)

enum class TaskStatus(val displayName: String, val color: Color, val bgColor: Color) {
    PENDING("Pending", Color(0xFFFFA726), Color(0x33FFA726)),
    IN_PROGRESS("In Progress", Color(0xFF42A5F5), Color(0x3342A5F5)),
    DONE("Done", Color(0xFF66BB6A), Color(0x3366BB6A))
}

// ------------------- Sample & Filter Functions -------------------

fun getSampleTasks(): List<Task> {
    return listOf(
        Task("Organize Workshop on Data Structures", TaskStatus.IN_PROGRESS, "John Doe", "2023-10-26"),
        Task("Prepare Quiz for Algorithms", TaskStatus.PENDING, "Jane Smith", "2023-10-28"),
        Task("Finalize Speaker for Tech Talk", TaskStatus.DONE, "Mike Johnson", "2023-10-30"),
        Task("Create Coding Challenge", TaskStatus.IN_PROGRESS, "Sarah Wilson", "2023-10-27"),
        Task("Prepare Study Materials", TaskStatus.PENDING, "David Brown", "2023-11-01")
    )
}

// ------------------- Main Composable Screen -------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dsa_Coordinator_Screen(navController: NavController) {
    var selectedBottomNavItem by remember { mutableStateOf(0) }
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tasks = remember { getSampleTasks() }

    val filteredTasks = when (selectedTabIndex) {
        0 -> tasks // All tasks
        1 -> tasks.filter { it.status == TaskStatus.PENDING }
        2 -> tasks.filter { it.status == TaskStatus.IN_PROGRESS }
        3 -> tasks.filter { it.status == TaskStatus.DONE }
        else -> tasks
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "DSA Coordinator Dashboard", navController = navController,
                fontFamily = FontFamily
            )
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
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .offset(x = 5.dp, y = 80.dp)
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
                .background(Color(0xFFF8FAFC))
        ) {
            StatusTabRow(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            // Task List
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredTasks) { task ->
                    TaskCard(task = task)
                }
            }
        }
    }
}

// TaskCard Component (using your DSA screen styling)
@Composable
fun TaskCard(task: Task) {
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

            // Status Chip with dot indicator
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(task.status.bgColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(task.status.color)
                    )
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
}

// ------------------- Preview -------------------

@Preview(showBackground = true)
@Composable
fun Dsa_CoordinatorScreenPreview() {
    MaterialTheme {
        Dsa_Coordinator_Screen(rememberNavController())
    }
}