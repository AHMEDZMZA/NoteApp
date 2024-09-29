package com.example.note_app.presentation.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.note_app.data.db.Note
import com.example.note_app.data.db.NoteDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    val noteDao = NoteDatabase.getDatabase(application).noteDao()

    private val _allNotes = MutableStateFlow<List<Note>?>(null)
    val allNotes: StateFlow<List<Note>?> get() = _allNotes


    init {
        getAllNotes()
    }


    fun getAllNotes() {
        viewModelScope.launch {
            _allNotes.value = noteDao.getAll()
            println("All notes: ${_allNotes.value}")
        }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
           getAllNotes()
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
            getAllNotes()

        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
            getAllNotes()
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            noteDao.deleteAll()
            getAllNotes()
        }

    }
}