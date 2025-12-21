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

val sampleNotes = listOf(
    Note(
        "1", "Buy groceries", false,
        LocalDateTime.parse("2025-08-25T20:04:14.775695")
    ),
    Note("2", "Attend meeting", isBookmarked = true),
    Note(
        "3",
        "Plan vacation",
        dateTime = LocalDateTime.parse("2025-08-26T16:04:14.774395")
    )
)

data class NotesUiState(
    val notesList: List<Note> = emptyList(),
    val newNoteText: String = "",
)

class NoteViewModel : ViewModel() {
    // Expose screen UI state as a StateFlow
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()


    init {
        // Simulate loading notes from a repository
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    notesList = sampleNotes
                )
            }
        }
    }

    fun addNote(content: String) {
        viewModelScope.launch {
            val newNote = Note(System.currentTimeMillis().toString(), content)
            _uiState.update { currentState -> currentState.copy(notesList = currentState.notesList + newNote) }
        }
    }

    fun toggleBookmark(noteId: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    currentState.notesList.map { note ->
                        if (note.id == noteId) note.copy(isBookmarked = !note.isBookmarked) else note
                    }
                )
            }
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    notesList= currentState.notesList.filter { note -> note.id != noteId }
                )
            }
        }
    }
}