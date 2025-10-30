import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.club_mangement_app.R
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val fontFamily = FontFamily(
    Font(R.font.f2, FontWeight.Normal),           // Normal weight for body text
    Font(R.font.f4, FontWeight.Thin),             // Thin for subtle text
    Font(R.font.f3, FontWeight.SemiBold),         // SemiBold for emphasis
    Font(R.font.italic, FontWeight.Light),        // Light Italic for highlights
    Font(R.font.variable, FontWeight.Medium),     // Medium for regular text
    Font(R.font.playfairdisplay_bold, FontWeight.Bold) // Bold for headings
)

data class Task(
    val id: Int,
    val title: String,
    val assignedTo: String,
    val deadline: String,
    val priority: Priority,
    val isCompleted: Boolean = false
)
enum class Priority {
    HIGH, MEDIUM, LOW
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoordinatorScreen() {
    var selectedMember by remember { mutableStateOf("Select member") }
    var taskName by remember { mutableStateOf("") }
    var selectedDeadline by remember { mutableStateOf<LocalDate?>(null) }

    val pendingTasks = listOf(
        Task(1, "Design UI/UX", "Ethan", "Oct 25", Priority.HIGH),
        Task(2, "Develop Backend", "Olivia", "Oct 28", Priority.MEDIUM)
    )

    val completedTasks = listOf(
        Task(3, "Initial Setup", "Noah", "Oct 20", Priority.LOW, true)
    )

    val members = listOf("Select member", "Ethan", "Olivia", "Noah")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_crowdsource_24),
                            contentDescription = "club management app",
                            tint = Color(0xFF4245F0),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(70.dp))
                        Text(
                            text = "Club Management",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = fontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                actions = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { /* Handle settings click */ }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F9FA) // Light grayish background
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(fontFamily = fontFamily)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF1F3F4)) // Light grayish background for content area
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProjectInfoCard(fontFamily = fontFamily)
            }

            item {
                AssignTaskSection(
                    taskName = taskName,
                    onTaskNameChange = { taskName = it },
                    selectedDeadline = selectedDeadline,
                    onDeadlineSelected = { selectedDeadline = it },
                    selectedMember = selectedMember,
                    onMemberSelected = { selectedMember = it },
                    members = members,
                    onAssignTask = {
                        // Handle task assignment
                    },
                    fontFamily = fontFamily
                )
            }

            item {
                TaskSection(
                    title = "Pending Tasks",
                    tasks = pendingTasks,
                    onDeleteTask = { /* Handle delete */ },
                    fontFamily = fontFamily
                )
            }

            item {
                TaskSection(
                    title = "Completed Tasks",
                    tasks = completedTasks,
                    onDeleteTask = { /* Handle delete */ },
                    fontFamily = fontFamily
                )
            }
        }
    }
}

@Composable
fun ProjectInfoCard(fontFamily: FontFamily) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF4245F0).copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_crowdsource_24),
                contentDescription = "Project Info",
                tint = Color(0xFF4245F0),
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Current Project: EcoTrack",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal // Uses f2 font
                )
                Text(
                    text = "Next Release: Oct 30",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold, // Uses f3 font
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDropdown(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate?) -> Unit,
    fontFamily: FontFamily
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant()
            ?.toEpochMilli()
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        // Date Display Field with Calendar Icon
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    Color(0xFFE8EAED), // Light gray background
                    RoundedCornerShape(8.dp)
                )
        ) {
            BasicTextField(
                value = selectedDate?.format(formatter) ?: "",
                onValueChange = { /* Read-only */ },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp, end = 56.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        if (selectedDate == null) {
                            Text(
                                text = "Select a date",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                fontFamily = fontFamily,
                                fontWeight = FontWeight.Thin // Uses f4 font
                            )
                        }
                        innerTextField()
                    }
                }
            )

            // Calendar Icon
            IconButton(
                onClick = { showDatePicker = true },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Select Date",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    }

    // Date Picker Dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onDateSelected(date)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(
                        "OK",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium // Uses variable font
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(
                        "Cancel",
                        fontFamily = fontFamily,
                        fontWeight = FontWeight.Medium // Uses variable font
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun AssignTaskSection(
    taskName: String,
    onTaskNameChange: (String) -> Unit,
    selectedDeadline: LocalDate?,
    onDeadlineSelected: (LocalDate?) -> Unit,
    selectedMember: String,
    onMemberSelected: (String) -> Unit,
    members: List<String>,
    onAssignTask: () -> Unit,
    fontFamily: FontFamily
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Assign a New Task",
                fontSize = 18.sp,
                fontWeight = FontWeight.Thin,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = fontFamily,
                fontStyle = FontStyle.Italic
            )

            // Task Name Input
            Column {
                Text(
                    text = "Task Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium, // Uses variable font
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.height(4.dp))
                BasicTextField(
                    value = taskName,
                    onValueChange = onTaskNameChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFE8EAED), // Light gray background
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp),
                    decorationBox = { innerTextField ->
                        Box {
                            if (taskName.isEmpty()) {
                                Text(
                                    text = "e.g., Design new logo",
                                    color = Color.Gray,
                                    fontFamily = fontFamily,
                                    fontWeight = FontWeight.Thin // Uses f4 font
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            // Deadline Input with Date Picker
            Column {
                Text(
                    text = "Deadline",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium, // Uses variable font
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.height(4.dp))
                DatePickerDropdown(
                    selectedDate = selectedDeadline,
                    onDateSelected = onDeadlineSelected,
                    fontFamily = fontFamily
                )
            }

            // Member Selection
            Column {
                Text(
                    text = "Assign to Member",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium, // Uses variable font
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = fontFamily
                )
                Spacer(modifier = Modifier.height(4.dp))
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFFE8EAED), // Light gray background
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { expanded = true }
                            .padding(12.dp)
                    ) {
                        Text(
                            text = selectedMember,
                            color = if (selectedMember == "Select member") {
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                            fontFamily = fontFamily,
                            fontWeight = if (selectedMember == "Select member") {
                                FontWeight.Thin // Uses f4 font
                            } else {
                                FontWeight.Normal // Uses f2 font
                            }
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color(0xFFF8F9FA)) // Light grayish background
                    ) {
                        members.forEach { member ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = member,
                                        fontFamily = fontFamily,
                                        fontWeight = if (member == "Select member") {
                                            FontWeight.Thin // Uses f4 font
                                        } else {
                                            FontWeight.Normal // Uses f2 font
                                        }
                                    )
                                },
                                onClick = {
                                    onMemberSelected(member)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Assign Button
            Button(
                onClick = onAssignTask,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4245F0)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Assign Task & Notify",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun TaskSection(
    title: String,
    tasks: List<Task>,
    onDeleteTask: (Int) -> Unit,
    fontFamily: FontFamily
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Thin,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = fontFamily,
            fontStyle = FontStyle.Italic
        )

        tasks.forEach { task ->
            TaskItem(
                task = task,
                onDelete = { onDeleteTask(task.id) },
                fontFamily = fontFamily
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onDelete: () -> Unit,
    fontFamily: FontFamily
) {
    val backgroundColor = if (task.isCompleted) {
        Color(0xFFE8F5E8)
    } else {
        MaterialTheme.colorScheme.surface
    }

    val borderColor = if (task.isCompleted) {
        Color(0xFF4CAF50)
    } else {
        Color(0xFF4245F0)
    }

    val textColor = if (task.isCompleted) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold, // Uses f3 font
                    color = textColor,
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    fontFamily = fontFamily
                )
                Text(
                    text = "Assigned to: ${task.assignedTo}",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal // Uses f2 font
                )
                Text(
                    text = "Deadline: ${task.deadline}",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.7f),
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal // Uses f2 font
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!task.isCompleted) {
                    PriorityBadge(priority = task.priority, fontFamily = fontFamily)
                }
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onDelete() }
                )
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority, fontFamily: FontFamily) {
    val (backgroundColor, textColor, label) = when (priority) {
        Priority.HIGH -> Triple(Color(0xFFFFF3E0), Color(0xFFF57C00), "High")
        Priority.MEDIUM -> Triple(Color(0xFFFFEBEE), Color(0xFFE91E63), "Medium")
        Priority.LOW -> Triple(Color(0xFFE8F5E8), Color(0xFF4CAF50), "Low")
    }

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium, // Uses variable font
            color = textColor,
            fontFamily = fontFamily
        )
    }
}

@Composable
fun BottomNavigationBar(fontFamily: FontFamily) {
    var selectedItem by remember { mutableStateOf("Dashboard") }

    val items = listOf(
        BottomNavItem("Dashboard", Icons.Outlined.Dashboard),
        BottomNavItem("Tasks", Icons.Outlined.TaskAlt),
        BottomNavItem("Chat", Icons.Outlined.ChatBubble),
        BottomNavItem("Profile", Icons.Outlined.Person)
    )

    BottomAppBar(
        containerColor = Color(0xFFF8F9FA), // Light grayish background
        contentColor = MaterialTheme.colorScheme.onSurface,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                items.forEach { item ->
                    BottomNavItemView(
                        item = item.copy(isSelected = item.label == selectedItem),
                        onItemClick = { selectedItem = item.label },
                        fontFamily = fontFamily
                    )
                }
            }
        }
    )
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val isSelected: Boolean = false
)

@Composable
fun BottomNavItemView(item: BottomNavItem, onItemClick: () -> Unit, fontFamily: FontFamily) {
    val color = if (item.isSelected) {
        Color(0xFF4245F0)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val fontWeight = if (item.isSelected) {
        FontWeight.Medium // Uses variable font
    } else {
        FontWeight.Normal // Uses f2 font
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onItemClick() }
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            fontSize = 12.sp,
            fontWeight = fontWeight,
            color = color,
            fontFamily = fontFamily
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CoordinatorScreenPreview() {
    MaterialTheme {
        CoordinatorScreen()
    }
}