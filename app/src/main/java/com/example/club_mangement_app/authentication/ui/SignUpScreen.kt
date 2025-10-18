package com.example.club_mangement_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.club_mangement_app.authentication.network.ApiService
import com.example.club_mangement_app.authentication.network.SignupRequest
import com.example.club_mangement_app.authentication.utils.SharedPrefManager
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun SignupScreen(
    sharedPrefManager: SharedPrefManager,
    onSignupSuccess: () -> Unit
) {

    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("https://swagserver.co.in/") // Replace with your API base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api = retrofit.create(ApiService::class.java)

    //  variables
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Member") }
    var domain by remember { mutableStateOf("DSA") }
    var message by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
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
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenuBox(
            label = "Role",
            options = listOf("Admin", "Coordinator", "Member"),
            selectedOption = role,
            onOptionSelected = { role = it }
        )
        Spacer(modifier = Modifier.height(8.dp))
        DropdownMenuBox(
            label = "Domain",
            options = listOf("Apps", "Web", "Graphics", "DSA"),
            selectedOption = domain,
            onOptionSelected = { domain = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = api.signup(SignupRequest(name, email, password, role, domain))
                        if (response.isSuccessful && response.body()?.success == true) {
                            response.body()?.user?.let { sharedPrefManager.saveUser(it) }
                            message = response.body()?.message ?: "Signup Successful"
                            onSignupSuccess()
                        } else {
                            message = response.body()?.message ?: "Signup Failed"
                        }
                    } catch (e: Exception) {
                        message = e.localizedMessage ?: "Error occurred"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(message)
    }
}

@Composable
fun DropdownMenuBox(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
