package com.example.note_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cafe.adriel.voyager.navigator.Navigator
import com.example.note_app.presentation.ui.MainScreen
import com.example.note_app.presentation.viewmodel.NoteViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
          enableEdgeToEdge()
        val noteViewModel = NoteViewModel(application)
        setContent {
            Navigator(screen = MainScreen(noteViewModel))
        }

    }
}
