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

data class WebTask(
    val title: String,
    val status: WebTaskStatus,
    val assignee: String,
    val assigneeAvatar: String,
    val deadline: String
)

enum class WebTaskStatus(val displayName: String, val color: Color, val bgColor: Color) {
    PENDING("Pending", Color(0xFFEF5350), Color(0x33EF5350)),
    IN_PROGRESS("In Progress", Color(0xFFFFB74D), Color(0x33FFB74D)),
    DONE("Done", Color(0xFF66BB6A), Color(0x3366BB6A))
}

data class WebBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Web_Coordinator_Screen() {
    val selectedFilter = remember { mutableStateOf("All") }

    val tasks = listOf(
        WebTask(
            title = "Update website's events page",
            status = WebTaskStatus.PENDING,
            assignee = "Alex",
            assigneeAvatar = "https://lh3.googleusercontent.com/aida-public/AB6AXuD6__fBc3QWZ7NoHnLkgoUFrTgo8U8RFA49lhXCsvlQgenOERpTkqeor2FRIJmmd9bXRjEHfO6lNf9T3vEjj31pR78PuCUi3TOCQngCwcPhd7VdWejTPFzPjH4izNTccZ-A2nj_iRtdEu2xX7A9e5biy7-FlnpXMIucp3xBvM3E2GGnwI1-v5TjaXpkTrtOfsBXC39qCQaUnpkTyv78DzrpPBB338LZyasMJQJQeD6fY1Zu48E3cDCcXVVkJ0RojXFKUhh_c8vvpD6J",
            deadline = "2023-10-27"
        ),
        WebTask(
            title = "Fix broken links on the blog",
            status = WebTaskStatus.IN_PROGRESS,
            assignee = "Maria",
            assigneeAvatar = "https://lh3.googleusercontent.com/aida-public/AB6AXuDsk_8gM0_L7it7l58juG3XDRAa84hlA1N0UueLR-jzkSdb0xyJHbtFTn1rpfLHk79uZzwlmiMltmKUOKSpAsWweTo8Q8PMA90j6kjJb3rpngGGw_8TIQ5g9radK0ScfAVX2_2EFkUWmqUWcpuPQNjBWxpWJrAije1BE5P_3pNDEgTaYtvLPyY4iG99ijiGRM581HRFcYYuEXOUMBh8Ru2Jj_HxSQJtKzs209P8xUoV3XxxEkkw3dH7uI4DB6MoWmwMrlGzwBWyuWtm",
            deadline = "2023-10-28"
        ),
        WebTask(
            title = "Design a new landing page",
            status = WebTaskStatus.DONE,
            assignee = "Chris",
            assigneeAvatar = "https://lh3.googleusercontent.com/aida-public/AB6AXuBFSz7-ZGdUCHNKmxyG1e64XqrbKWrh0GedbDyxuZwoPYmWjBwhyn5_VA2VtH7kahRUhxoxf4tnASc7wAj2JMPhgJKSO2NnG4xwZXaC8PrHaV03jO1zA9fXd-UOQS12njUgf5ktuzDuazV9Xw8_H9Sfn9_mR-ekUOhDqdofx6Y-SnHSbJjCyg3XptUrCI8oF44Wg_7B_txRSbFOM4JzhawGTreb1QRvW7_rNA1Fsubgsg4sJKDWXuq8eP-hWwp_NsPyX4WIcMw68TDM",
            deadline = "2023-11-05"
        )
    )

    val bottomNavItems = listOf(
        WebBottomNavItem("Dashboard", Icons.Default.Dashboard, "dashboard"),
        WebBottomNavItem("Tasks", Icons.Default.TaskAlt, "tasks"),
        WebBottomNavItem("Chat", Icons.AutoMirrored.Filled.Chat, "chat"),
        WebBottomNavItem("Profile", Icons.Default.Person, "profile"),
        WebBottomNavItem("Settings", Icons.Default.Settings, "settings")
    )
    val fontFamily = FontFamily(
        Font(R.font.f2, FontWeight.Medium),
        Font(R.font.f4, FontWeight.Thin),
        Font(R.font.f3, FontWeight.SemiBold),
        Font(R.font.italic, FontWeight.Bold),
        Font(R.font.variable, FontWeight.Normal),
        Font(R.font.playfairdisplay_bold, FontWeight.ExtraLight)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "web Domain Dashboard",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
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
                containerColor = Color(0xFFFBBC05),
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
                    WebBottomNavItem(
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
                    WebFilterChip(
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
                    WebTaskCard(task = task)
                }
            }
        }
    }
}

@Composable
fun WebFilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) {
        Color(0xFFFBBC05) // Solid yellow when selected
    } else {
        Color(0xFFE8EAED) // Light gray for unselected chips
    }

    val textColor = if (isSelected) {
        Color.Black // Black text for better contrast on yellow
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = if (isSelected) Color(0xFFFBBC05) else Color.Transparent,
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
fun WebTaskCard(task: WebTask) {
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
                    .background(Color(0xFFFBBC05)) // Yellow background for avatar
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Assignee",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = Color.Black // Black icon for better contrast on yellow
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

@Composable
fun WebBottomNavItem(
    item: WebBottomNavItem,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val textColor = if (isSelected) {
        Color(0xFFFBBC05) // Yellow color for selected state
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val iconColor = if (isSelected) {
        Color(0xFFFBBC05) // Yellow color for selected state
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
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Web_Coordinator_ScreenPreview() {
    MaterialTheme {
        Web_Coordinator_Screen()
    }
}