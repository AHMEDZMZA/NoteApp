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
import com.example.note_app.data.db.Note
@Composable
fun HomeScreenContent(noteViewModel: NoteViewModel, searchQuery: String) {
    val allNotes by noteViewModel.allNotes.collectAsState(initial = emptyList())
    val filteredNotes = allNotes?.let { searchNotes(it, searchQuery) }
    val navigator = LocalNavigator.currentOrThrow

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Padding for better alignment
    ) {
        items(filteredNotes ?: emptyList()) { note ->
            SwipeToDeleteContainer(item = note, onDelete = {
                noteViewModel.deleteNote(note)
            }) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(note.color.toInt())),
                    elevation = CardDefaults.cardElevation(8.dp), // Adjusted elevation for soft shadow
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigator.push(NoteDetailsScreen(note, noteViewModel)) }
                        .padding(vertical = 8.dp) // Vertical padding for better spacing between cards
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
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = if (note.isFavorite) Color.Red else Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp)) // Space between icon and title
                        Text(
                            text = note.title,
                            fontSize = 22.sp,
                            color = Color.Black, // Title color
                            modifier = Modifier.align(Alignment.Start) // Align title to the start
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = note.description,
                            fontSize = 18.sp,
                            color = Color.DarkGray // Description color
                        )
                        Spacer(modifier = Modifier.height(16.dp)) // Space between description and next item
                    }
                }
            }
        }
    }
}


@Composable
fun searchNotes(notes: List<Note>, query: String): List<Note> {
    if (query.isEmpty()) {
        return notes // Return all notes if the search query is empty
    }
    return notes.filter { note ->
        note.title.contains(query, ignoreCase = true) ||
                note.description.contains(query, ignoreCase = true)
    }
}

