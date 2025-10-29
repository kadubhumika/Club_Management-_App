package com.example.club_mangement_app.chat

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.filled.ArrowDropDown

data class ChatMessage(
    val sender: String,
    val message: String,
    val time: String,
    val isCurrentUser: Boolean,
    val avatarRes: Int? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    var messageText by remember { mutableStateOf("") }
    var selectedDomain by remember { mutableStateOf("App") }
    var showDomainDropdown by remember { mutableStateOf(false) }

    val domainOptions = listOf("App", "Web", "Graphics")

    val messages = remember {
        listOf(
            ChatMessage("Jane Doe", "Don't forget the meeting at 3 PM!", "2:30 PM", false),
            ChatMessage("John Doe", "I'll be there, thanks for the reminder!", "2:31 PM", true),
            ChatMessage("Alice", "Great, see you then!", "2:32 PM", false),
            ChatMessage("John Doe", "Looking forward to it.", "2:35 PM", true)
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Chat",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick= { navController.navigateUp() }

                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        bottomBar = {
            MessageInputBar(
                messageText = messageText,
                onMessageChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        // Handle send message
                        messageText = ""
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // Tabs
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = Color(0xFF5B50F5),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFF5B50F5),
                        height = 3.dp
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            "Domain Chat",
                            fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            "General Club Chat",
                            fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                            color = Color.Gray
                        )
                    }
                )
            }

            // Domain Dropdown (only show in Domain Chat tab)
            if (selectedTab == 0) {
                Surface(
                    color = Color.White,
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = showDomainDropdown,
                            onExpandedChange = { showDomainDropdown = it }
                        ) {
                            OutlinedTextField(
                                value = selectedDomain,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Select Domain") },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown",
                                        tint = Color(0xFF5B50F5)
                                    )
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF5B50F5),
                                    unfocusedBorderColor = Color.LightGray,
                                    focusedLabelColor = Color(0xFF5B50F5)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )

                            ExposedDropdownMenu(
                                expanded = showDomainDropdown,
                                onDismissRequest = { showDomainDropdown = false }
                            ) {
                                domainOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                text = option,
                                                fontWeight = if (option == selectedDomain)
                                                    FontWeight.Bold else FontWeight.Normal
                                            )
                                        },
                                        onClick = {
                                            selectedDomain = option
                                            showDomainDropdown = false
                                            // Handle domain selection change
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                }

                Divider(color = Color.LightGray, thickness = 1.dp)
            }


            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(messages) { message ->
                    ChatMessageItem(message)
                }
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!message.isCurrentUser) {
            Text(
                text = message.sender,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 56.dp, bottom = 4.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (message.isCurrentUser) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            if (!message.isCurrentUser) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            if (message.isCurrentUser) {
                Text(
                    text = message.time,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(end = 8.dp)
                )
            }

            Surface(
                shape = RoundedCornerShape(
                    topStart = if (message.isCurrentUser) 20.dp else 4.dp,
                    topEnd = if (message.isCurrentUser) 4.dp else 20.dp,
                    bottomStart = 20.dp,
                    bottomEnd = 20.dp
                ),
                color = if (message.isCurrentUser) Color(0xFF5B50F5) else Color.White,
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Text(
                    text = message.message,
                    color = if (message.isCurrentUser) Color.White else Color.Black,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            if (!message.isCurrentUser) {
                Text(
                    text = message.time,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .padding(start = 8.dp)
                )
            }

            if (message.isCurrentUser) {
                Spacer(modifier = Modifier.width(8.dp))
                // Avatar for outgoing messages
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }
        }

        if (message.isCurrentUser) {
            Text(
                text = message.sender,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 56.dp, top = 4.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}

@Composable
fun MessageInputBar(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageChange,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp),
                placeholder = {
                    Text(
                        "Type a message...",
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.LightGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                singleLine = false,
                maxLines = 4
            )

            FloatingActionButton(
                onClick = onSendClick,
                containerColor = Color(0xFF5B50F5),
                contentColor = Color.White,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showBackground=true, showSystemUi=true)
@Composable
fun ChatScreenPreview(){
    val navController=rememberNavController()
    ChatScreen(navController=navController)
}