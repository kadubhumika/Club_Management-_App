package com.example.club_mangement_app.components

// ui/components/ProjectTile.kt

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.club_mangement_app.ui.theme.BlueStatus
import com.example.club_mangement_app.ui.theme.EmeraldSecondary
import com.example.club_mangement_app.ui.theme.OrangeStatus

// Helper class for data (replace with your actual data model later)
data class ProjectData(
    val domain: String,
    val name: String,
    val color: Color,
    val activeSprints: Int,
    val members: Int,
    val coordinators: Int
)

@Composable
fun ProjectTile(
    project: ProjectData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        ),
        border = BorderStroke(2.dp, project.color.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = project.domain,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = project.color
                )
            )
            Spacer(modifier = Modifier.height(4.dp))


            Text(
                text = project.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProjectStat(
                    label = "Active Sprints",
                    value = project.activeSprints,
                    color = BlueStatus
                )
                ProjectStat(
                    label = "Members",
                    value = project.members,
                    color = EmeraldSecondary
                )
                ProjectStat(
                    label = "Coordinators",
                    value = project.coordinators,
                    color = OrangeStatus
                )
            }

            TextButton(onClick = { /* Handle navigation to Project Details */ }) {
                Text("Tap to enter", color = project.color)
            }
        }
    }
}

@Composable
fun ProjectStat(label: String, value: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}