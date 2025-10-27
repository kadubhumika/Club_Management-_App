package com.example.club_mangement_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.components.DropdownBox
import com.example.club_mangement_app.components.OutlinedTextBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    var fullName by remember { mutableStateOf("Alex Doe") }
    var email by remember { mutableStateOf("alex.doe@example.com") }
    var phone by remember { mutableStateOf("+1 123 456 7890") }
    var bio by remember { mutableStateOf("Passionate about mobile development and creating intuitive user experiences.") }
    var role by remember { mutableStateOf("Officer") }
    var domain by remember { mutableStateOf("Tech") }

    val roleOptions = listOf("Officer", "Manager", "Developer", "Designer")
    val domainOptions = listOf("Tech", "Marketing", "Finance", "HR")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = { /* Cancel */ }) {
                    Text("Cancel", color = Color.Gray)
                }
                Button(onClick = { /* Save Changes */ },
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Save Changes")
                }
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    painter = painterResource(id = R.drawable.swag_logo),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
                IconButton(
                    onClick = { /* Change photo */ },
                    modifier = Modifier
                        .offset(x = 10.dp, y = 10.dp)
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF3B82F6))
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Edit Photo",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextBox(fullName, { fullName = it }, "Full Name")
            OutlinedTextBox(email, { email = it }, "Email Address", KeyboardType.Email)
            OutlinedTextBox(phone, { phone = it }, "Phone Number", KeyboardType.Phone)
            OutlinedTextBox(bio, { bio = it }, "Short Bio", maxLines = 3)

            DropdownBox("Role", roleOptions, role) { role = it }
            DropdownBox("Domain", domainOptions, domain) { domain = it }
        }
    }
}

@Preview(showBackground=true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    val navController=rememberNavController()
    EditProfileScreen(navController=navController)
}
