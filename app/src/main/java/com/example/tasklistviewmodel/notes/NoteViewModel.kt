package com.example.tasklistviewmodel.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteViewModel : ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        // Simulate loading notes from a repository
        viewModelScope.launch {
            _notes.value = listOf(
                Note(
                    "1", "Buy groceries" , false , LocalDateTime.parse("2025-08-25T20:04:14.775695")
                ),
                Note("2", "Attend meeting", isBookmarked = true),
                Note("3", "Plan vacation" , dateTime = LocalDateTime.parse("2025-08-26T16:04:14.774395"))
            )
        }
    }

    fun addNote(content: String) {
        viewModelScope.launch {
            val newNote = Note(System.currentTimeMillis().toString(), content)
            _notes.update { currentNotes -> currentNotes + newNote }
        }
    }

    fun toggleBookmark(noteId: String) {
        viewModelScope.launch {
            _notes.update { currentNotes ->
                currentNotes.map { note ->
                    if (note.id == noteId) note.copy(isBookmarked = !note.isBookmarked) else note
                }
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            _notes.update { currentNotes ->
                currentNotes.filter { note -> note.id != noteId }
            }
        }
    }
}