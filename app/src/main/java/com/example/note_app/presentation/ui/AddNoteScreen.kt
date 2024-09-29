package com.example.note_app.presentation.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNote(
    onSaveClick: (String, String, Int) -> Unit, // Include color as an Int parameter
    onDismissRequest: () -> Unit
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedColor = remember { mutableStateOf(Color.White) } // Default color

    // قائمة بالألوان المتناسقة
    val colorsList = listOf(
        Color(0xFFB0BEC5), // Light Gray
        Color(0xFF4CAF50), // Green
        Color(0xFF2196F3), // Blue
        Color(0xFFFFEB3B), // Yellow
        Color(0xFFF44336), // Red
        Color(0xFF9C27B0)  // Purple
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.Close,
            contentDescription = null,
            modifier = Modifier
                .clickable { onDismissRequest() }
                .align(alignment = Alignment.End)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add New Note",
            fontSize = 25.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            placeholder = {
                Text(
                    text = "Add Title",
                    color = Color.Black
                )
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.height(100.dp),
            value = description.value,
            onValueChange = { description.value = it },
            placeholder = {
                Text(
                    text = "Add Description",
                    color = Color.Black
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Color picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            colorsList.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color)
                        .border(
                            border = if (color == selectedColor.value) {
                                BorderStroke(3.dp, Color.Black)
                            } else {
                                BorderStroke(1.dp, Color.Gray)
                            },
                        )
                        .clickable { selectedColor.value = color }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onSaveClick(title.value, description.value, selectedColor.value.toArgb())
        }) {
            Text(text = "Save Note", color = Color.White)
        }
    }
}
