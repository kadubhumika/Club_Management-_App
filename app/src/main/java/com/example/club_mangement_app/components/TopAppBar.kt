package com.example.club_mangement_app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(title: String) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notification")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(

            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

