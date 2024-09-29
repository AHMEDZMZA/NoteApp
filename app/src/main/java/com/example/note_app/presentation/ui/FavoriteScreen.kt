package com.example.note_app.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.note_app.presentation.viewmodel.NoteViewModel
import com.example.note_app.SwipeToDeleteContainer

@Composable
fun FavoriteScreenContent(noteViewModel: NoteViewModel) {
    val allNotes by noteViewModel.allNotes.collectAsState(initial = emptyList())
    val navigator = LocalNavigator.currentOrThrow

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Added padding for better layout alignment
    ) {
        allNotes?.let {
            items(
                it.filter { it.isFavorite } // Filter only favorite notes
            ) { note ->
                SwipeToDeleteContainer(item = note, onDelete = {
                    noteViewModel.deleteNote(it)
                }) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(note.color.toInt())), // Dynamic color
                        elevation = CardDefaults.cardElevation(8.dp), // Soft elevation for a smooth shadow
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigator.push(NoteDetailsScreen(note, noteViewModel)) }
                            .padding(vertical = 8.dp) // Vertical padding for spacing between cards
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        noteViewModel.updateNote(note.copy(isFavorite = !note.isFavorite))
                                    }
                                    .align(Alignment.End)
                                    .size(28.dp),
                                imageVector = Icons.Filled.Favorite, // Changed to Star icon
                                contentDescription = null,
                                tint = if (note.isFavorite) Color.Red else Color.White // Yellow for favorites
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Space between icon and title
                            Text(
                                text = note.title,
                                fontSize = 22.sp, // Increased title size for distinction
                                color = Color.Black, // Title color
                                modifier = Modifier.align(Alignment.Start) // Align title to the start
                            )
                            Spacer(modifier = Modifier.height(8.dp)) // Space between title and description
                            Text(
                                text = note.description,
                                fontSize = 18.sp, // Smaller font for the description
                                color = Color.DarkGray // Description color
                            )
                            Spacer(modifier = Modifier.height(16.dp)) // Space between description and next item
                        }
                    }
                }
            }
        }
    }
}
