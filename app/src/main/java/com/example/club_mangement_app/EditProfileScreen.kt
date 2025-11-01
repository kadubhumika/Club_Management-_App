package com.example.club_mangement_app

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.club_mangement_app.authentication.network.ApiService
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import com.example.club_mangement_app.components.DropdownBox
import com.example.club_mangement_app.components.OutlinedTextBox
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefManager = remember { SharedPrefManager(context) }
    var user by remember { mutableStateOf(sharedPrefManager.getUser()) }

    LaunchedEffect(Unit) {
        user = sharedPrefManager.getUser()
    }


    var fullName by remember { mutableStateOf(user?.name ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var role by remember { mutableStateOf(user?.role ?: "") }
    var domain by remember { mutableStateOf(user?.domain ?: "") }
    var phone by remember { mutableStateOf("+91 99324567") }
    var bio by remember { mutableStateOf("Passionate Developer who loves learning new things!") }
    var profileImage by remember { mutableStateOf(user?.profile_img ?: "") }

    val roleOptions = listOf("Admin", "Coordinator", "Member", "Designer")
    val domainOptions = listOf("App", "Web", "Graphics")

    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://swagserver.co.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api = retrofit.create(ApiService::class.java)

    val scope = rememberCoroutineScope()
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            scope.launch {
                val uploadedUrl = uploadImageToServer(context, it, email, api)
                if (uploadedUrl != null) {
                    profileImage = uploadedUrl
                    val updatedUser = user?.copy(profile_img = uploadedUrl)
                    if (updatedUser != null) sharedPrefManager.saveUser(updatedUser)
                }
            }
        }
    }

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
                    .padding(start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 32.dp ),

                horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { navController.navigateUp() }) {
                    Text("Cancel", color = Color.Gray)
                }
                Button(
                    onClick = {
                        val updatedUser = user?.copy(
                            name = fullName,
                            email = email,
                            role = role,
                            domain = domain,
                            profile_img = profileImage
                        )
                        if (updatedUser != null) {
                            sharedPrefManager.saveUser(updatedUser)
                            navController.navigateUp()
                        }
                    },
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
                .verticalScroll(rememberScrollState())
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                if (profileImage.isNotEmpty()) {
                    AsyncImage(
                        model = profileImage,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.swag_logo),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                    )
                }

                IconButton(
                    onClick = { imagePicker.launch("image/*") },
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

suspend fun uploadImageToServer(
    context: Context,
    uri: Uri,
    email: String,
    api: ApiService
): String? = withContext(Dispatchers.IO) {
    try {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "upload_temp.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val emailBody = email.toRequestBody("text/plain".toMediaTypeOrNull())

        val response: Response<ResponseBody> = api.uploadImage(body, emailBody)

        if (response.isSuccessful) {
            val json = JSONObject(response.body()?.string() ?: "")
            if (json.getBoolean("success")) {
                val imageUrl = json.getString("url")
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, " Image uploaded!", Toast.LENGTH_SHORT).show()
                }
                return@withContext imageUrl
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "❌ ${json.getString("message")}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
    null
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileScreenPreview() {
    val navController = rememberNavController()
    EditProfileScreen(navController = navController)
}
