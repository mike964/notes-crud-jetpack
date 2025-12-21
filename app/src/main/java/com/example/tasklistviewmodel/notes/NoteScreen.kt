package com.example.tasklistviewmodel.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NoteScreen(noteViewModel: NoteViewModel = viewModel()) {
    val uiState by noteViewModel.uiState.collectAsState()
    var newNoteContent by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (newNoteContent.isNotBlank()) {
                    noteViewModel.addNote(newNoteContent)
                    newNoteContent = ""
                }
            }) {
                Icon(Icons.Filled.Add, "Add new note")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                value = newNoteContent,
                onValueChange = { newNoteContent = it },
                label = { Text("New Note") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            )
            LazyColumn {
                items(uiState.notesList) { note ->
                    NoteItem(
                        note = note,
                        onToggleBookmark = { noteViewModel.toggleBookmark(note.id) },
                        onDeleteNote = { noteViewModel.deleteNote(note.id) }
                    )
                }
            }
        }
    }
}