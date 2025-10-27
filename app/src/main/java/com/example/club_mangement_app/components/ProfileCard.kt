package com.example.club_mangement_app.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.R


data class Member(
    val name: String,
    val role: String,
    val imageRes: Int
)

@Composable
fun ProfileCard(member: Member, modifier: Modifier = Modifier, navController: NavController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = member.imageRes),
            contentDescription = member.name,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(Modifier.width(12.dp))

        // Name & Role
        Column {
            Text(
                text = member.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )
            Text(
                text = member.role,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    val navController=rememberNavController()
    ProfileCard(member = Member("Android Pro", "Developer", R.drawable.ic_launcher_background), navController=navController)
}



