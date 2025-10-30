package com.example.club_mangement_app.coordinator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.club_mangement_app.R
import com.example.club_mangement_app.components.*

data class WebTask(
    val title: String,
    val status: WebTaskStatus,
    val assignee: String,
    val assigneeAvatar: String,
    val deadline: String
)

enum class WebTaskStatus(val displayName: String, val color: Color, val bgColor: Color) {
    PENDING("Pending", Color(0xFFEF5350), Color(0x33EF5350)),
    IN_PROGRESS("In Progress", Color(0xFFFFB74D), Color(0x33FFB74D)),
    DONE("Done", Color(0xFF66BB6A), Color(0x3366BB6A))
}

data class WebBottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun Web_Coordinator_Screen(navController: NavController) {

    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedBottomNavItem by remember { mutableStateOf(0) }

    val webTasks = listOf(
        WebTask(
            title = "Update website's events page",
            status = WebTaskStatus.PENDING,
            assignee = "Alex",
            assigneeAvatar = "https://lh3.googleusercontent.com/aida-public/AB6AXuD6__fBc3QWZ7NoHnLkgoUFrTgo8U8RFA49lhXCsvlQgenOERpTkqeor2FRIJmmd9bXRjEHfO6lNf9T3vEjj31pR78PuCUi3TOCQngCwcPhd7VdWejTPFzPjH4izNTccZ-A2nj_iRtdEu2xX7A9e5biy7-FlnpXMIucp3xBvM3E2GGnwI1-v5TjaXpkTrtOfsBXC39qCQaUnpkTyv78DzrpPBB338LZyasMJQJQeD6fY1Zu48E3cDCcXVVkJ0RojXFKUhh_c8vvpD6J",
            deadline = "2023-10-27"
        ),
        WebTask(
            title = "Fix broken links on the blog",
            status = WebTaskStatus.IN_PROGRESS,
            assignee = "Maria",
            assigneeAvatar = "https://lh3.googleusercontent.com/aida-public/AB6AXuDsk_8gM0_L7it7l58juG3XDRAa84hlA1N0UueLR-jzkSdb0xyJHbtFTn1rpfLHk79uZzwlmiMltmKUOKSpAsWweTo8Q8PMA90j6kjJb3rpngGGw_8TIQ5g9radK0ScfAVX2_2EFkUWmqUWcpuPQNjBWxpWJrAije1BE5P_3pNDEgTaYtvLPyY4iG99ijiGRM581HRFcYYuEXOUMBh8Ru2Jj_HxSQJtKzs209P8xUoV3XxxEkkw3dH7uI4DB6MoWmwMrlGzwBWyuWtm",
            deadline = "2023-10-28"
        ),
        WebTask(
            title = "Design a new landing page",
            status = WebTaskStatus.DONE,
            assignee = "Chris",
            assigneeAvatar = "https://lh3.googleusercontent.com/aida-public/AB6AXuBFSz7-ZGdUCHNKmxyG1e64XqrbKWrh0GedbDyxuZwoPYmWjBwhyn5_VA2VtH7kahRUhxoxf4tnASc7wAj2JMPhgJKSO2NnG4xwZXaC8PrHaV03jO1zA9fXd-UOQS12njUgf5ktuzDuazV9Xw8_H9Sfn9_mR-ekUOhDqdofx6Y-SnHSbJjCyg3XptUrCI8oF44Wg_7B_txRSbFOM4JzhawGTreb1QRvW7_rNA1Fsubgsg4sJKDWXuq8eP-hWwp_NsPyX4WIcMw68TDM",
            deadline = "2023-11-05"
        )
    )

    val fontFamily = FontFamily(
        Font(R.font.f2, FontWeight.Medium),
        Font(R.font.f4, FontWeight.Thin),
        Font(R.font.f3, FontWeight.SemiBold),
        Font(R.font.italic, FontWeight.Bold),
        Font(R.font.variable, FontWeight.Normal),
        Font(R.font.playfairdisplay_bold, FontWeight.ExtraLight)
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "App Coordinator Dashboard", navController = navController,
                fontFamily = FontFamily
            )
        },
        bottomBar = {
            AppBottomNavBar(
                selectedItem = selectedBottomNavItem,
                onItemSelected = { index -> selectedBottomNavItem = index },
                navController = navController
            )
        },
        floatingActionButton = {
            WebAddAssignmentFAB(onClick = { navController.navigate("assignTaskRoute") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
        ) {

            StatusTabRow(
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(webTasks) { task ->
                    WebTaskCard(task = task)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebAppTopBar(
    title: String,
    navController: NavController,
    fontFamily: FontFamily
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_crowdsource_24),
                    contentDescription = "club management app",
                    tint = Color(0xFF4245F0),
                    modifier = Modifier.size(32.dp)
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Handle notifications */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF8F9FA),
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun WebAddAssignmentFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFFFBBC05),
        modifier = Modifier
            .padding(bottom = 80.dp)
            .offset(x =  5.dp , y = 80.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Task",
            tint = Color.White
        )
    }
}

@Composable
fun WebTaskCard(task: WebTask) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFBBC05))
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Assignee",
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    tint = Color.Black
                )
            }

            // Task Details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Text(
                    text = "Deadline: ${task.deadline}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(task.status.bgColor)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = task.status.displayName,
                    color = task.status.color,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeb_Coordinator_Screen() {
    Web_Coordinator_Screen(rememberNavController())
}