package com.example.club_mangement_app.contributionMap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import com.example.club_mangement_app.components.AppBottomNavBar
import com.example.club_mangement_app.components.AppTopBar
import fontFamily

@Composable
fun ProfileScreen(
    onNavigate: (Int) -> Unit, navController: NavController
) {
    var selectedBottomNavItem by remember { mutableStateOf(3) }

    Scaffold(
        topBar = {
            AppTopBar(title = "Profile", navController = navController, fontFamily = FontFamily.Companion)
        },
        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index ->
                    selectedBottomNavItem = index
                    onNavigate(index)


                },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            ProfileHeader()
            Spacer(modifier = Modifier.height(24.dp))
            ContributionMapSection()
            Spacer(modifier = Modifier.height(32.dp))
            EditProfileButton(navController=navController)
        }
    }
}

@Composable
fun ProfileHeader() {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    val user = sharedPrefManager.getUser()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Avatar
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFFFDDB3)),
            contentAlignment = Alignment.Center
        ) {
            val profileUrl = user?.profile_img

            if (!profileUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = profileUrl,
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(60.dp),
                    tint = Color(0xFF8B6F47)
                )
            }
        }


        Spacer(modifier = Modifier.width(20.dp))

       // damnn broo goo workk now
        Column {
            Text(
                text = user?.name ?: "Unknown User",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user?.role ?: "No role assigned",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = user?.domain ?: "No domain",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = user?.email ?: "No email found",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ContributionMapSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        Text(
            text = "Contribution Map",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contribution Grid
        ContributionGrid()

        Spacer(modifier = Modifier.height(12.dp))

        // Legend
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Less",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            ContributionLegendBox(Color(0xFFE8F5E9))
            Spacer(modifier = Modifier.width(4.dp))
            ContributionLegendBox(Color(0xFF81C784))
            Spacer(modifier = Modifier.width(4.dp))
            ContributionLegendBox(Color(0xFF4CAF50))
            Spacer(modifier = Modifier.width(4.dp))
            ContributionLegendBox(Color(0xFF2E7D32))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "More",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ContributionGrid() {
    // Sample contribution data
    val contributions = listOf(
        0, 2, 3, 3, 4, 4, 3, 2, 0, 2, 3, 4,
        3, 2, 0, 2, 3, 2, 0, 2, 3, 3, 4,
        4, 4, 3, 2, 0, 2, 3, 4, 3, 2, 0, 2,
        3, 2, 0, 2, 3, 4, 4, 4, 3, 2, 0, 2,
        3, 4, 3, 2, 0, 2, 3, 2, 0, 2, 3, 4,
        3, 4, 3, 4, 3, 2, 0, 2, 3, 3, 3, 2,
        0, 2, 3, 2, 0, 2, 3, 4, 4, 4, 3, 2
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(12),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.height(200.dp)
    ) {
        items(contributions.size) { index ->
            ContributionBox(contributions[index])
        }
    }
}

@Composable
fun ContributionBox(level: Int) {
    val color = when (level) {
        0 -> Color(0xFFEEEEEE)
        1 -> Color(0xFFC8E6C9)
        2 -> Color(0xFF81C784)
        3 -> Color(0xFF4CAF50)
        4 -> Color(0xFF2E7D32)
        else -> Color(0xFFEEEEEE)
    }

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color)
    )
}

@Composable
fun ContributionLegendBox(color: Color) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color)
    )
}

@Composable
fun EditProfileButton(navController : NavController) {
    Button(
        onClick = { navController.navigate("edit_profile") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF5B5FED)
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Edit Profile",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

