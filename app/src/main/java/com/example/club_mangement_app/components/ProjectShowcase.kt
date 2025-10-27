package com.example.club_mangement_app.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// *** IMPORTANT: Define ProjectData ONLY HERE ***
data class ProjectData(
    val domain: String,
    val status: String,
    val activeProjects: Int,
    val activeSprints: Int,
    val statusColor: Color = Color(0xFF6A5ACD)
)

@Composable
fun AdminProjectCard(data: ProjectData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = data.domain, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = data.status, color = data.statusColor, fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Active Domain", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = Color.LightGray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(12.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(text = "${data.activeProjects}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Text(text = "Active Projects", color = Color.Gray, fontSize = 12.sp)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "${data.activeSprints}", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    Text(text = "Active Sprints", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}


@Composable
fun MetricBox(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F2F7)), // Light background
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, color = Color.Gray, fontSize = 14.sp)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}