package com.example.club_mangement_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StatusPill(status: String, pillColor: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = pillColor.copy(alpha = 0.2f),
        contentColor = pillColor, // Status text color
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Text(
            text = status,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

data class TaskData(
    val name: String,
    val assignee: String,
    val deadline: String,
    val status: String,
    val statusColor: Color,
)

@Composable
fun TaskRowItem(data: TaskData) {

    val statusColor = when (data.status) {
        "In Progress" -> Color(0xFFFFA726) // Orange
        "Pending" -> Color(0xFF64B5F6) // Light Blue
        "Done" -> Color(0xFF66BB6A) // Green
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Task Title and Status Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = data.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                StatusPill(status = data.status, pillColor = statusColor)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Assignee",
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = data.assignee,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(16.dp))


                Text(
                    text = "Deadline: ${data.deadline}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


// --- Task Card (Container, mostly for the domain dashboard screen) ---
@Composable
fun TaskCard(
    domainTitle: String,
    progressPercent: String,
    tasks: List<TaskData>, // Now accepts list of full TaskData
    taskColor: Color = Color(0xFFE6E6FA)
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        // Header (DSA Domain Dashboard | 80%)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = domainTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = progressPercent,
                color = Color.Black.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
        }

        // List of TaskRowItem (The actual list of tasks)
        tasks.forEach { task ->
            TaskRowItem(data = task)
        }
    }
}


// --- PREVIEW FUNCTION (For the main container on the dashboard) ---
@Preview(showBackground = true)
@Composable
fun PreviewTaskCardRefined() {
    val sampleTasks = listOf(
        TaskData("Organize Workshop on Data Structures", "John Doe", "2023-10-26", "In Progress", Color(0xFFFFA726)),
        TaskData("Prepare Quiz for Algorithms", "Jane Smith", "2023-10-28", "Pending", Color(0xFF64B5F6)),
        TaskData("Finalize Speaker for Tech Talk", "Mike Johnson", "2023-10-30", "Done", Color(0xFF66BB6A))
    )

    MaterialTheme {
        Surface(color = Color(0xFFF3F2F7)) { // Match the light background of the app
            Column(modifier = Modifier.padding(16.dp)) {
                TaskCard(
                    domainTitle = "DSA Domain Dashboard",
                    progressPercent = "65%",
                    tasks = sampleTasks
                )
            }
        }
    }
}