package com.example.club_mangement_app.info


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.R
import com.example.club_mangement_app.components.Member
import com.example.club_mangement_app.components.ProfileCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@Composable
fun ClubInformationPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
            .padding(16.dp)
    ) {
        // Header
        // Header with Back Arrow
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Club Information",
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        // Mission Card
        Card(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "This is the club's mission, goals, and what it offers to members. " +
                        "It is a vibrant community of tech enthusiasts passionate about learning " +
                        "and building things together. We aim to foster a collaborative environment " +
                        "where students can explore technology, develop practical skills, and work on exciting projects.",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                ),
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        // Sections using ProfileCard
        Section(
            sectionTitle = "Data Structures & Algorithms",
            members = listOf(
                Member("Jane Doe", "Domain Lead", R.drawable.ic_launcher_background),
                Member("John Smith", "Coordinator", R.drawable.ic_launcher_background)
            ),
            navController = navController
        )

        Section(
            sectionTitle = "Web Development",
            members = listOf(Member("Emily White", "Domain Lead", R.drawable.ic_launcher_background)),
            navController = navController
        )

        Section(
            sectionTitle = "App Development",
            members = listOf(
                Member("Michael Brown", "Domain Lead", R.drawable.ic_launcher_background),
                Member("Sarah Green", "Coordinator", R.drawable.ic_launcher_background)
            ),
            navController = navController
        )

        Section(
            sectionTitle = "Graphics",
            members = listOf(Member("David Black", "Domain Lead", R.drawable.ic_launcher_background)),
            navController = navController
        )
    }
}

@Composable
fun Section(sectionTitle: String, members: List<Member>, navController: NavController) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(
            text = sectionTitle,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(Modifier.height(4.dp))
        members.forEach { member ->
            ProfileCard(member, navController = navController)
        }
    }
}

@Preview(showBackground=true, showSystemUi=true)
@Composable
fun ClubInformationPagePreview(){
    val navController=rememberNavController()
    ClubInformationPage(navController=navController)
}
