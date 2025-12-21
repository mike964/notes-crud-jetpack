package com.example.tasklistviewmodel.notes

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

val sampleNotes = listOf(
    Note(
        "1", "Buy groceries", false,
        LocalDateTime.parse("2025-08-25T20:04:14.775695")
    ),
    Note("2", "Attend meeting", true),
    Note(
        "3",
        "Plan vacation",
        dateTime = LocalDateTime.parse("2025-08-26T16:04:14.774395")
    )
)

data class NotesUiState(
    val notesList: List<Note> = emptyList(),
    val newNoteText: String = "",
    val searchQuery: String = "",
    val importantIsToggled: Boolean = false,  // show only important notes
)

class NoteViewModel : ViewModel() {
    // Expose screen UI state as a StateFlow
    private val _uiState = MutableStateFlow(NotesUiState())
    val uiState: StateFlow<NotesUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _filteredNotes = MutableStateFlow(_uiState.value.notesList)
    val filteredNotes: StateFlow<List<Note>> = _filteredNotes


    fun toggleFilter() {
        Log.d("xx", "toggleFilter()---")
//        showImportant = !showImportant
//        filterImportantNotes(_uiState.value.notesList, !showImportant)
        _uiState.value =
            _uiState.value.copy(
                importantIsToggled = !_uiState.value.importantIsToggled,

            )
       filterImportantNotes()

    }

    init {
        // Simulate loading notes from a repository
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    notesList = sampleNotes
                )
            }
        }
        filterNotes(_searchQuery)
    }

    private fun filterNotes(searchQry: MutableStateFlow<String>) {
        // Combine the search query flow with the allNotes list (conceptually)
        // This ensures filtering happens whenever the query changes.
        searchQry
            .onEach { query ->
                _filteredNotes.value = _uiState.value.notesList.filter { note ->
//                    note.title.contains(query, ignoreCase = true) ||
                    note.content.contains(query, ignoreCase = true)
                }
            }
            .launchIn(viewModelScope) // Use viewModelScope to keep the flow active
    }

    private fun filterImportantNotes( )  {
        if (_uiState.value.importantIsToggled) {
          _filteredNotes.value =   _uiState.value.notesList.filter { note -> note.isImportant }
        } else {
            _filteredNotes.value =  _uiState.value.notesList
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
                        if (note.id == noteId) note.copy(isImportant = !note.isImportant) else note
                    }
                )
            }
           filterImportantNotes()
        }
    }

    fun deleteNote(noteId: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    notesList = currentState.notesList.filter { note -> note.id != noteId }
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

}