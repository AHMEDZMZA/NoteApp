package com.example.note_app.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import cafe.adriel.voyager.core.screen.Screen
import com.example.note_app.presentation.viewmodel.NoteViewModel
import com.example.note_app.data.db.Note
import com.example.note_app.ui.theme.Note_AppTheme

class MainScreen(val noteViewModel: NoteViewModel) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var expanded by remember { mutableStateOf(false) }
        val selectedIndex = remember { mutableStateOf(0) }
        val showAddScreen = remember { mutableStateOf(false) }
        val searchQuery = remember { mutableStateOf("") }
        val isSearchActive = remember { mutableStateOf(false) }
        Note_AppTheme {
            Scaffold(
                floatingActionButton = {
                    if (selectedIndex.value == 0 && !showAddScreen.value) {
                        FloatingActionButton(
                            onClick = { showAddScreen.value = true },
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                },
                topBar = {
                    if (!showAddScreen.value && selectedIndex.value != 2) {
                        TopAppBar(
                            actions = {
                                // Search Icon
                                if (!isSearchActive.value) {
                                    IconButton(onClick = {
                                        isSearchActive.value = true
                                        expanded = false // Hide menu when searching
                                    }) {
                                        Icon(Icons.Default.Search, contentDescription = null)
                                    }
                                } else {
                                    // Back Arrow Icon
                                    IconButton(onClick = {
                                        isSearchActive.value = false
                                    }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                                    }
                                }

                                // Search TextField
                                if (isSearchActive.value) {
                                    TextField(
                                        value = searchQuery.value,
                                        onValueChange = { searchQuery.value = it },
                                        placeholder = { Text("Search...") },
                                        trailingIcon = {
                                            IconButton(onClick = { searchQuery.value = "" }) {
                                                Icon(Icons.Default.Close, contentDescription = null)
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    )
                                }

                                // Menu Icon
                                if (!isSearchActive.value) {
                                    IconButton(onClick = {
                                        expanded = !expanded
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = null)
                                    }
                                    if (expanded) {
                                        Popup(
                                            alignment = Alignment.TopEnd,
                                            onDismissRequest = { expanded = false }
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .width(200.dp)
                                                    .background(Color.Gray),
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable {
                                                            expanded = false
                                                            // Handle delete action
                                                        }
                                                        .padding(16.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        Icons.Default.Delete,
                                                        contentDescription = null,
                                                        tint = Color.White
                                                    )
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Text(
                                                        text = "Delete",
                                                        color = Color.White,
                                                        fontSize = 20.sp
                                                    )
                                                }
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable {
                                                            expanded = false
                                                            // Handle settings action
                                                        }
                                                        .padding(16.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(
                                                        Icons.Default.Settings,
                                                        contentDescription = null,
                                                        tint = Color.White
                                                    )
                                                    Spacer(modifier = Modifier.width(10.dp))
                                                    Text(
                                                        text = "Settings",
                                                        color = Color.White,
                                                        fontSize = 20.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Gray,
                                titleContentColor = Color.White
                            ),
                            title = { Text(text = "Notes", fontSize = 28.sp) }
                        )
                    }
                },
                bottomBar = {
                    if (!showAddScreen.value) {
                        BottomAppBar(containerColor = Color.Gray) {
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { selectedIndex.value = 0 }) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = null,
                                    tint = if (selectedIndex.value == 0) Color.Black else Color.White
                                )
                            }
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { selectedIndex.value = 1 }) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = null,
                                    tint = if (selectedIndex.value == 1) Color.Black else Color.White
                                )
                            }
                            IconButton(
                                modifier = Modifier.weight(1f),
                                onClick = { selectedIndex.value = 2 }) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null,
                                    tint = if (selectedIndex.value == 2) Color.Black else Color.White
                                )
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    if (showAddScreen.value) {
                        AddNote(
                            onSaveClick = { title, description, color ->
                                noteViewModel.insertNote(
                                    Note(
                                        title = title,
                                        description = description,
                                        isFavorite = false,
                                        color = color.toLong()
                                    )
                                )
                                showAddScreen.value = false
                            },
                            onDismissRequest = {
                                showAddScreen.value = false
                            }
                        )
                    } else {
                        when (selectedIndex.value) {
                            0 -> HomeScreenContent(noteViewModel, searchQuery.value)
                            1 -> FavoriteScreenContent(noteViewModel = noteViewModel)
                            2 -> ProfileScreenContent(noteViewModel = noteViewModel)
                        }
                    }
                }
            }
        }
    }
}