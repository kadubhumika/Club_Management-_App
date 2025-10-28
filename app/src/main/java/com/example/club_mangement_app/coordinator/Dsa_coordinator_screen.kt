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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
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

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

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

fun getFilteredTasks(tasks: List<Task>, filter: String): List<Task> {
    return when (filter) {
        "Pending" -> tasks.filter { it.status == TaskStatus.PENDING }
        "In Progress" -> tasks.filter { it.status == TaskStatus.IN_PROGRESS }
        "Done" -> tasks.filter { it.status == TaskStatus.DONE }
        else -> tasks
    }
}

// ------------------- Main Composable Screen -------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dsa_Coordinator_Screen() {
    val selectedFilter = remember { mutableStateOf("All") }
    val tasks = remember { getSampleTasks() }
    val filteredTasks = remember(selectedFilter.value) {
        getFilteredTasks(tasks, selectedFilter.value)
    }

    val fontFamily = FontFamily(
        Font(R.font.f2, FontWeight.Medium),
        Font(R.font.f4, FontWeight.Thin),
        Font(R.font.f3, FontWeight.SemiBold),
        Font(R.font.italic, FontWeight.Bold),
        Font(R.font.variable, FontWeight.Normal),
        Font(R.font.playfairdisplay_bold, FontWeight.ExtraLight)
    )

    val bottomNavItems = listOf(
        BottomNavItem("Dashboard", Icons.Default.Dashboard, "dashboard"),
        BottomNavItem("Tasks", Icons.Default.Checklist, "tasks"),
        BottomNavItem("Chat", Icons.AutoMirrored.Filled.Chat, "chat"),
        BottomNavItem("Profile", Icons.Default.Person, "profile"),
        BottomNavItem("Settings", Icons.Default.Settings, "settings")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DSA Domain Dashboard",
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
                    IconButton(onClick = { /* Handle notification */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F9FA),
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Handle add task */ },
                containerColor = Color(0xFF66BB6A),
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
                containerColor = Color(0xFFF8F9FA),
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                )
            ) {
                bottomNavItems.forEach { item ->
                    DsaBottomNavItem(
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
                .background(Color(0xFFF1F3F4))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                listOf("All", "Pending", "In Progress", "Done").forEach { filter ->
                    DsaFilterChip(
                        text = filter,
                        isSelected = filter == selectedFilter.value,
                        onClick = { selectedFilter.value = filter }
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredTasks) { task ->
                    DsaTaskCard(task = task)
                }
            }
        }
    }
}

// ------------------- Reusable UI Components -------------------

@Composable
fun DsaFilterChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF66BB6A) else Color(0xFFE8EAED)
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFF66BB6A) else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun DsaTaskCard(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFF66BB6A))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Assignee",
                    modifier = Modifier.size(24.dp).align(Alignment.Center),
                    tint = Color.White
                )
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.SemiBold,
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

            Box(
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    .background(task.status.bgColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier.size(8.dp).clip(CircleShape).background(task.status.color)
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

@Composable
fun DsaBottomNavItem(item: BottomNavItem, isSelected: Boolean, onItemClick: () -> Unit) {
    val color = if (isSelected) Color(0xFF66BB6A) else MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(onClick = onItemClick, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = item.icon, contentDescription = item.label, tint = color)
        }
        Text(
            text = item.label,
            color = color,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

// ------------------- Preview -------------------

@Preview(showBackground = true)
@Composable
fun Dsa_CoordinatorScreenPreview() {
    MaterialTheme {
        Dsa_Coordinator_Screen()
    }
}
