package com.example.club_mangement_app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AddAssignmentFAB(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,

        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Assign New Task")
    }
}