package com.example.club_mangement_app.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.components.AppBottomNavBar


data class AppNotification(
    val id: Int,
    val title: String,
    val message: String,
    val time: String,
    val type: NotificationType,
    val isRead: Boolean = false
)

enum class NotificationType(val icon: ImageVector, val color: Color, val bgColor: Color) {
    EVENT(Icons.Default.Event, Color(0xFF4CAF50), Color(0x334CAF50)),       // Green
    TASK(Icons.Default.Assignment, Color(0xFF2196F3), Color(0x332196F3)),   // Blue
    GROUP(Icons.Default.Group, Color(0xFFFF9800), Color(0x33FF9800)),       // Orange
    GENERAL(Icons.Default.Notifications, Color(0xFF9C27B0), Color(0x339C27B0)) // Purple
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceholderTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF8FAFC), // Matching your dashboard background
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}


@Composable
fun NotificationItemCard(notification: AppNotification) {
    val containerColor = if (notification.isRead) {
        MaterialTheme.colorScheme.surface
    } else {
        Color(0xFFE3F2FD)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
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
                    .background(notification.type.color)
            ) {
                Icon(
                    imageVector = notification.type.icon,
                    contentDescription = notification.type.name,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = Color.White
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = notification.title,
                    fontWeight = if (notification.isRead) FontWeight.Normal else FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Text(
                    text = notification.message,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Time Stamp
            Text(
                text = notification.time,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    var selectedBottomNavItem by remember { mutableStateOf(0) } // Assuming a default selected item

    val notifications = listOf(
        AppNotification(1, "New Event Added", "Club meeting scheduled for next Monday.", "10m ago", NotificationType.EVENT, isRead = false),
        AppNotification(2, "Task Assigned", "Review new member applications for next week.", "2h ago", NotificationType.TASK, isRead = false),
        AppNotification(3, "Group Message", "Check out the new discussion in 'Projects' group.", "5h ago", NotificationType.GROUP, isRead = true),
        AppNotification(4, "System Update", "New features are now available on the dashboard!", "Yesterday", NotificationType.GENERAL, isRead = true),
        AppNotification(5, "Event Reminder", "Your training session starts in 1 hour. Be ready.", "Yesterday", NotificationType.EVENT, isRead = true),
    ).groupBy { if (!it.isRead) "Unread" else "Previously Seen" } // Group by status

    Scaffold(
        topBar = {
            PlaceholderTopBar(title = "Notifications", navController = navController)
        },
        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index -> selectedBottomNavItem = index },
                navController = navController
            )
        },
        containerColor = Color(0xFFF8FAFC) // Matching your dashboard background color
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF8FAFC)),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            notifications.forEach { (groupName, notifs) ->

                item {
                    Text(
                        text = groupName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(notifs) { notification ->
                    NotificationItemCard(notification = notification)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    MaterialTheme {
        NotificationScreen(rememberNavController())
    }
}