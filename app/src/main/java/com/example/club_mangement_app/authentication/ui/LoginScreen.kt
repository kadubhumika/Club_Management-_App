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

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            api.login(LoginRequest(email, password, "", "")) // No role/domain here
                        }
                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful && response.body()?.success == true) {
                                val user = response.body()?.user
                                if (user != null) {
                                    sharedPrefManager.saveUser(user)
                                    onLoginSuccess(user.role)
                                }
                                message = "Login Successful"
                            } else {
                                message = response.body()?.message ?: "Login Failed"
                            }
                        }
                    } catch (e: Exception) {
                        message = e.localizedMessage ?: "Error"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
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
