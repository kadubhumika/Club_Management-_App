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
import androidx.compose.ui.modifier.modifierLocalOf
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

data class GraphicsTask(
    val title: String,
    val status: GraphicsTaskStatus,
    val assignee: String,
    val assigneeAvatar: String,
    val deadline: String
)

enum class GraphicsTaskStatus(val displayName: String, val color: Color, val bgColor: Color) {
    PENDING("Pending", Color(0xFFFFA726), Color(0x33FFA726)),
    IN_PROGRESS("In Progress", Color(0xFF42A5F5), Color(0x3342A5F5)),
    DONE("Done", Color(0xFF66BB6A), Color(0x3366BB6A))
}

@Composable
fun Graphics_Coordinator_Screen(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedBottomNavItem by remember { mutableStateOf(0) }

    val graphicsTasks = listOf(
        GraphicsTask(
            title = "Design Event Poster",
            status = GraphicsTaskStatus.PENDING,
            assignee = "John Doe",
            assigneeAvatar = "https://example.com/avatar1.jpg",
            deadline = "2023-10-27"
        ),
        GraphicsTask(
            title = "Create Social Media Post for Event X",
            status = GraphicsTaskStatus.IN_PROGRESS,
            assignee = "Jane Smith",
            assigneeAvatar = "https://example.com/avatar2.jpg",
            deadline = "2023-10-28"
        ),
        GraphicsTask(
            title = "Design Club T-shirt",
            status = GraphicsTaskStatus.DONE,
            assignee = "Peter Jones",
            assigneeAvatar = "https://example.com/avatar3.jpg",
            deadline = "2023-10-29"
        )
    )

    val filteredTasks = when (selectedTabIndex) {
        0 -> graphicsTasks // All tasks
        1 -> graphicsTasks.filter { it.status == GraphicsTaskStatus.PENDING }
        2 -> graphicsTasks.filter { it.status == GraphicsTaskStatus.IN_PROGRESS }
        3 -> graphicsTasks.filter { it.status == GraphicsTaskStatus.DONE }
        else -> graphicsTasks
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Graphics Coordinator Dashboard", navController = navController,
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
                onClick = { navController.navigate("assignTaskRoute") },
                containerColor = Color(0xFF800080), // Purple
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
        ) {

            StatusTabRow(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredTasks) { task ->
                    GraphicsTaskCard(task = task)
                }
            }
        }
    }
}

@Composable
fun GraphicsTaskCard(task: GraphicsTask) {
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
                    .background(Color(0xFF800080)) // Purple background for avatar
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

@Preview(showBackground = true)
@Composable
fun PreviewGraphics_Coordinator_Screen() {
    Graphics_Coordinator_Screen(rememberNavController())
}