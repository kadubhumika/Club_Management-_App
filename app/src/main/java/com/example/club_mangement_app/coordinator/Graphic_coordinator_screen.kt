package com.example.club_mangement_app.coordinator

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.club_mangement_app.R

val fontFamily = FontFamily(
    Font(R.font.f2, FontWeight.Normal),
    Font(R.font.f4, FontWeight.Thin),
    Font(R.font.f3, FontWeight.SemiBold),
    Font(R.font.italic, FontWeight.Light),
    Font(R.font.variable, FontWeight.Medium),
    Font(R.font.playfairdisplay_bold, FontWeight.Bold)
)

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

data class GraphicsBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

// Function to get sample graphics tasks
fun getSampleGraphicsTasks(): List<GraphicsTask> {
    return listOf(
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
        ),
        GraphicsTask(
            title = "Update Website Banner",
            status = GraphicsTaskStatus.DONE,
            assignee = "John Doe",
            assigneeAvatar = "https://example.com/avatar4.jpg",
            deadline = "2023-10-25"
        ),
        GraphicsTask(
            title = "Create Event Flyer",
            status = GraphicsTaskStatus.PENDING,
            assignee = "Sarah Wilson",
            assigneeAvatar = "https://example.com/avatar5.jpg",
            deadline = "2023-10-30"
        ),
        GraphicsTask(
            title = "Design Social Media Story",
            status = GraphicsTaskStatus.IN_PROGRESS,
            assignee = "Mike Brown",
            assigneeAvatar = "https://example.com/avatar6.jpg",
            deadline = "2023-10-26"
        )
    )
}

// Function to get filtered tasks based on status
fun getFilteredGraphicsTasks(tasks: List<GraphicsTask>, filter: String): List<GraphicsTask> {
    return when (filter) {
        "Pending" -> tasks.filter { it.status == GraphicsTaskStatus.PENDING }
        "In Progress" -> tasks.filter { it.status == GraphicsTaskStatus.IN_PROGRESS }
        "Done" -> tasks.filter { it.status == GraphicsTaskStatus.DONE }
        else -> tasks
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Graphics_Coordinator_Screen() {
    val selectedFilter = remember { mutableStateOf("All") }
    val tasks = remember { getSampleGraphicsTasks() }
    val filteredTasks = remember(selectedFilter.value) {
        getFilteredGraphicsTasks(tasks, selectedFilter.value)
    }

    val bottomNavItems = listOf(
        GraphicsBottomNavItem("Dashboard", Icons.Default.Dashboard, "dashboard"),
        GraphicsBottomNavItem("Tasks", Icons.Default.TaskAlt, "tasks"),
        GraphicsBottomNavItem("Chat", Icons.AutoMirrored.Filled.Chat, "chat"),
        GraphicsBottomNavItem("Profile", Icons.Default.Person, "profile"),
        GraphicsBottomNavItem("Settings", Icons.Default.Settings, "settings")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Graphics Dashboard",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_crowdsource_24),
                            contentDescription = "club management app",
                            tint = Color(0xFF4245F0),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle notifications */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F9FA), // Light grayish background
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle add task */ },
                containerColor = Color(0xFF800080), // Purple
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Task",
                    tint = Color.White
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = Color(0xFFF8F9FA), // Light grayish background
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {
                bottomNavItems.forEach { item ->
                    GraphicsBottomNavItem(
                        item = item,
                        isSelected = item.label == "Dashboard",
                        onItemClick = { /* Handle navigation */ }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF1F3F4)) // Light grayish background for content area
        ) {
            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("All", "Pending", "In Progress", "Done").forEach { filter ->
                    GraphicsFilterChip(
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
                items(filteredTasks) { task ->
                    GraphicsTaskCard(task = task)
                }
            }
        }
    }
}

@Composable
fun GraphicsFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xFF800080) // Purple when selected
    } else {
        Color(0xFFE8EAED) // Light gray for unselected chips
    }

    val textColor = if (isSelected) {
        Color.White
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF800080) else Color.Transparent,
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
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily
        )
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
                    maxLines = 2,
                    fontFamily = fontFamily
                )
                Text(
                    text = "Deadline: ${task.deadline}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = fontFamily
                )
            }

            // Status Chip
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
                        fontWeight = FontWeight.Medium,
                        fontFamily = fontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun GraphicsBottomNavItem(
    item: GraphicsBottomNavItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val textColor = if (isSelected) {
        Color(0xFF800080) // Purple color for selected state
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val iconColor = if (isSelected) {
        Color(0xFF800080) // Purple color for selected state
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Column(
        modifier = Modifier
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            onClick = onItemClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = item.label,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
            fontFamily = fontFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Graphics_Coordinator_ScreenPreview() {
    MaterialTheme {
        Graphics_Coordinator_Screen()
    }
}