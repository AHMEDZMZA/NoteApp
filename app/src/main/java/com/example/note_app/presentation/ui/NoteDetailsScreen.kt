package com.example.note_app.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.note_app.presentation.viewmodel.NoteViewModel
import com.example.note_app.data.db.Note


class NoteDetailsScreen(val note: Note, val noteViewModel: NoteViewModel) : Screen {
    @Composable
    override fun Content() {
        val title = remember { mutableStateOf(note.title) }
        val description = remember { mutableStateOf(note.description) }
        val navigator = LocalNavigator.currentOrThrow
        val gradient = Brush.verticalGradient(
            colors = listOf(Color(0xFFFF5722), Color(0xFFFFC107))
        )

        Column(modifier = Modifier
            .fillMaxSize()
            .background(gradient)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navigator.pop() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Note Details", fontSize = 20.sp, color = Color.White)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(text = "Add Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = { Text(text = "Add Description") },
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = {
                        noteViewModel.updateNote(note.copy(title = title.value, description = description.value))
                        navigator.pop()
                    }) {
                        Text(text = "Update")
                    }

                    Button(onClick = {
                        noteViewModel.deleteNote(note)
                        navigator.pop()
                    }) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}
