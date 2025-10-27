package com.example.club_mangement_app.coordinator



import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.components.*

@Composable
fun MemberDashboardScreen(navController: NavController) {

    var selectedBottomNavItem by remember { mutableStateOf(0) }

    val assignedTasks = listOf(
        MemberTaskData(
            title = "Finalize App Icon Design",
            domain = "Design",
            status = "Pending",
            statusColor = Color(0xFFFFC107)
        ),
        MemberTaskData(
            title = "Onboarding Screen UI",
            domain = "Frontend",
            status = "In Progress",
            statusColor = Color(0xFF29B6F6)
        ),
        MemberTaskData(
            title = "User Profile Backend",
            domain = "Backend",
            status = "Done",
            statusColor = Color(0xFF8BC34A)
        ),
    )

    val notifications = listOf(
        NotificationData(
            type = "Task Reminder",
            message = "Finalize app icon is due tomorrow.",
            timeAgo = "1 hour ago",
            iconColor = Color(0xFF64B5F6) // Blue
        ),
        NotificationData(
            type = "New Announcement",
            message = "Club meeting scheduled for Friday",
            timeAgo = "2 hours ago",
            iconColor = Color(0xFF66BB6A) // Green
        ),
        NotificationData(
            type = "Task Completed",
            message = "User Profile Backend has been marked as completed.",
            timeAgo = "Yesterday",
            iconColor = Color(0xFFFF8A65) // Reddish-Orange
        ),
    )

    Scaffold(

        topBar = { AppTopBar(title = "Member Personal Dashboard") },

        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index -> selectedBottomNavItem = index }
            )
        },

        containerColor = Color(0xFFF0F0F5)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(8.dp))
                MemberProfileCard(userName = "Praveen")
            }


            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Assigned Tasks",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )

                    OutlinedButton(
                        onClick = { /* Handle Filter Click */ },
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color.White,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                        border = BorderStroke(1.dp, Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Filter")
                    }


                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(assignedTasks) { task ->
                MemberAssignedTaskCard(data = task)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Notifications",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }


            item {

                NotificationListCard(notifications = notifications)
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMemberDashboardScreen() {
    MemberDashboardScreen(navController = rememberNavController())
}