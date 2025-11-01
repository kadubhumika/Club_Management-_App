package com.example.club_mangement_app.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.club_mangement_app.authentication.utils.SharedPrefManager


data class MemberTaskData(
    val title: String,
    val domain: String,
    val status: String,
    val statusColor: Color
)


data class NotificationData(
    val type: String,
    val message: String,
    val timeAgo: String,
    val iconColor: Color
)


@Composable
fun MemberProfileCard() {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val user = sharedPrefManager.getUser()

    val userName = user?.name ?: "User"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Welcome back,",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Text(
            text = userName,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
    }
}



@Composable
fun MemberAssignedTaskCard(data: MemberTaskData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = data.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )

                StatusPill(status = data.status, pillColor = data.statusColor)
            }
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = data.domain,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun NotificationTile(data: NotificationData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = data.type,
            tint = data.iconColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = data.type, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(text = data.message, fontSize = 13.sp, color = Color.Gray)
        }
        Text(text = data.timeAgo, fontSize = 11.sp, color = Color.LightGray)
    }
}


@Composable
fun NotificationListCard(notifications: List<NotificationData>) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,

        shadowElevation = 0.dp,

        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            notifications.forEach { notification ->
                NotificationTile(data = notification)

                if (notifications.indexOf(notification) < notifications.size - 1) {
                    Divider(color = Color.Black.copy(alpha = 0.5f), thickness = 1.5.dp)
                }
            }
        }
    }
}