package com.example.club_mangement_app.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.club_mangement_app.R



private val organizer = Person("Gajanan Palepwad", "Club Organizer", R.drawable.gajanandada, "Google Developers Group on Campus Organizer Lead 2024-2025")

private val coordinators = listOf(
    Person("Yash Waghmare", "President", R.drawable.yashwaghmare),
    Person("Prachi Dafre", "GDG Lead", R.drawable.prachi)
)

private val events = listOf(
    Event("Hackfusion 2.0", "HackFusion 2.0 was a 36-hour national-level hackathon held from February 21–23, 2025 at SGGSIE&T, Nanded. Organized by GDG on Campus, it brought together tech enthusiasts to solve real-world problems in AI, web, and app development, with a ₹2 lakh prize pool and a focus on innovation, teamwork, and creativity.", R.drawable.hack),
    Event("Hackfusion", "The SWAG Developers Club at SGGSIE&T successfully organized two major technical events in 2023. The first, HackFusion, held on February 9, 2023, was an intense 36-hour coding extravaganza where participants built innovative solutions competing for a significant ₹90,000 prize pool. This was followed by WebVerse on April 9, 2023, a web development competition where students showcased their coding and design skills by creating themed websites to win a ₹10,000 cash prize.", R.drawable.hack2),
    Event("WebVerse", "WebVerse 2023 was a successful web development competition held by the SWAG club (SGGSIE&T) on April 9, 2023, where participants had to design and style a functional website from provided HTML code under a theme and time constraint, with the goal of winning a significant cash prize of 10,000rs.", R.drawable.webverse)
)

private val projects = listOf(
    Event("PSB iDEA Hackathon", "Team Swag won ₹1,00,000 at a national-level Fintech Hackathon organized by Union Bank of India and the Department of Financial Services.\n\nThe winning solution included AI-powered chatbots, face recognition with SIM binding for security, automated customer ticketing, and a personalized recommendation engine.", R.drawable.id),
    Event("Innovo’25 Hackathon", "The SGGSIE&T team secured the 1st Runner-Up position at the Innovo'25 Hackathon (Feb 15, 2025), a 36-hour event at SSGMCE Shegaon focused on future careers and sustainability. Mentored by the Tech Club, the team developed a solution featuring an ML-powered career path prediction system, gamified elements for skill learning, and real-time visual analytics for tracking progress.", R.drawable.shegaon),
    Event("Hackspectra Hackathon", "The SGGSIE&T team won the Hackspectra Hackathon (Feb 22-23, 2025) at MGM Nanded with their comprehensive College Management System Website. This 24-hour project digitizes academic and administrative tasks through a secure, role-based platform. It provides separate, tailored panels for Admins (user/request management), Faculty (timetables, attendance), and Students (academics, assignments, booking requests) to streamline all college operations.", R.drawable.hack_3)
)


data class Person(val name: String, val role: String, val imageResId: Int, val description: String? = null)
data class Event(val title: String, val description: String, val imageResId: Int)
data class BottomNavItem(val label: String, val icon: ImageVector, val selected: Boolean = false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubInfoScreen() {


    var selectedItem by remember { mutableStateOf(0) }
    val bottomNavItems = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Settings", Icons.Default.Settings),
        BottomNavItem("Profile", Icons.Default.Person),
        BottomNavItem("Notification", Icons.Default.Notifications)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Club Info", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {

            BottomNavigationBar(
                items = bottomNavItems,
                selectedItemIndex = selectedItem,
                onItemClick = { index -> selectedItem = index }
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { paddingValues ->


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                SectionTitle("Organizer")
            }
            item {
                OrganizerCard(person = organizer)
            }

            item {
                SectionTitle("Coordinators")
            }
            item {
                CoordinatorsRow(coordinators = coordinators)
            }


            item {
                SectionTitle("Annual Tech Events")
            }

            items(events) { event ->
                EventProjectCard(item = event)
            }


            item {
                SectionTitle("Achievements & Projects")
            }

            items(projects) { project ->
                EventProjectCard(item = project)
            }
        }
    }
}




@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 4.dp, top = 8.dp)
    )
}


@Composable
fun OrganizerCard(person: Person) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = person.imageResId),
                contentDescription = person.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(person.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Text(person.role, fontSize = 14.sp, color = Color.Gray)
                person.description?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(it, fontSize = 14.sp, color = Color.DarkGray, lineHeight = 20.sp)
                }
            }
        }
    }
}


@Composable
fun CoordinatorsRow(coordinators: List<Person>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        coordinators.forEach { person ->
            CoordinatorCard(person = person, modifier = Modifier.weight(1f))
        }
    }
}


@Composable
fun CoordinatorCard(person: Person, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = person.imageResId),
                contentDescription = person.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(person.name, fontWeight = FontWeight.SemiBold)
            Text(person.role, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun EventProjectCard(item: Event) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(item.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(item.description, fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}



@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    selectedItemIndex: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label, fontSize = 11.sp) },
                selected = selectedItemIndex == index,
                onClick = { onItemClick(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f)
                )
            )
        }
    }
}


@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun ClubInfoScreenPreview() {
    ClubInfoScreen()

}