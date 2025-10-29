package com.example.club_mangement_app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.club_mangement_app.authentication.network.ApiService
import com.example.club_mangement_app.authentication.network.LoginRequest
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    sharedPrefManager: SharedPrefManager,
    onLoginSuccess: (String) -> Unit,
    onSignupClick: () -> Unit
) {
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://swagserver.co.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api = retrofit.create(ApiService::class.java)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    fun isCollegeEmail(email: String): Boolean {
        return email.endsWith("@sggs.ac.in", ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back 👋",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("College Email (e.g. 2024bcs095@sggs.ac.in)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (!isCollegeEmail(email)) {
                    message = "Please use your college email (e.g. 2024bcs095@sggs.ac.in)"
                    return@Button
                }
                if (password.isBlank()) {
                    message = "Please enter your password"
                    return@Button
                }

                coroutineScope.launch {
                    loading = true
                    try {
                        val response = withContext(Dispatchers.IO) {
                            api.login(LoginRequest(email, password, "", ""))
                        }
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful && response.body()?.success == true) {
                                val user = response.body()?.user
                                if (user != null) {
                                    sharedPrefManager.saveUser(user)
                                    when (user.role) {
                                        "Admin" -> navController.navigate("admin_dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                        "Coordinator" -> when (user.domain) {
                                            "Web" -> navController.navigate("web_coordinator_dashboard") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                            "Apps" -> navController.navigate("app_coordinator_dashboard") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                            "Graphics" -> navController.navigate("graphics_coordinator_dashboard") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                            "DSA" -> navController.navigate("dsa_coordinator_dashboard") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                            else -> navController.navigate("member_dashboard") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        }
                                        else -> navController.navigate("member_dashboard") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }

                                }
                                message = "Login Successful 🎉"
                            } else {
                                message = response.body()?.message ?: "Invalid credentials"
                            }
                        }
                    } catch (e: Exception) {
                        message = e.localizedMessage ?: "Network error"
                    } finally {
                        loading = false
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(message, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Don't have an account? ")
            Text(
                text = "Sign up",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onSignupClick() }
            )
        }
    }
}
