package com.example.note_app.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.note_app.presentation.viewmodel.NoteViewModel
import com.example.note_app.R

@Composable
fun ProfileScreenContent(noteViewModel: NoteViewModel) {
    val navigator = LocalNavigator.currentOrThrow
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF3E2723), Color(0xFF000000))
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp), // Adding padding for better layout
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .border(2.dp, Color.Black, CircleShape),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = email.value,
                onValueChange = { newValue ->
                    email.value = newValue
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Username Icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                placeholder = { Text("Username") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { newValue ->
                    password.value = newValue
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Password Icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    // Add your save functionality here
                }) {
                    Text(text = "Save")
                }

                Button(onClick = {
                    // Add your edit functionality here
                }) {
                    Text(text = "Edit")
                }
            }
        }
    }
}
