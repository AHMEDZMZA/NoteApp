package com.example.note_app.data.db

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title :String,
    val description :String,
    val isFavorite :Boolean,
    val color : Long
)
