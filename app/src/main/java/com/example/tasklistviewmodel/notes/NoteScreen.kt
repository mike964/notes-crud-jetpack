package com.example.tasklistviewmodel.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasklistviewmodel.TextToggleButton


@Composable
fun NoteScreen(vm: NoteViewModel = viewModel()) {
    val uiState by vm.uiState.collectAsState()
    val searchQuery by vm.searchQuery.collectAsState()
    val filteredNotes by vm.filteredNotes.collectAsState()
    var newNoteContent by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (newNoteContent.isNotBlank()) {
                    vm.addNote(newNoteContent)
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
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(2f).padding(12.dp)) {
                    // Search TextField
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { vm.onSearchQueryChanged(it) },
                        label = { Text("Search Notes") },
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    TextToggleButton("Important", uiState.importantIsToggled){
                        vm.toggleFilter()
                    }
                    Text( uiState.importantIsToggled.toString())
                }
            }
            LazyColumn {
                items(filteredNotes) { note ->
                    NoteItem(
                        note = note,
                        onToggleBookmark = { vm.toggleBookmark(note.id) },
                        onDeleteNote = { vm.deleteNote(note.id) }
                    )
                }
            }
        }
    }
}