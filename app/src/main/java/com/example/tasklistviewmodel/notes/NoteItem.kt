package com.example.tasklistviewmodel.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter

@Composable
fun NoteItem(note: Note, onToggleBookmark: () -> Unit, onDeleteNote: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { /* Handle note click, e.g., edit note */ }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    note.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toString()
                )
                Text(text = note.content, fontWeight = FontWeight.Bold)
            }
            Column {
            Row {
                IconButton(onClick = onToggleBookmark) {
                    Icon(
                        if (note.isImportant) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Bookmark",
                        tint = if (note.isImportant) Color.Magenta else Color.LightGray
                    )
                }
                IconButton(onClick = onDeleteNote) {
                    Icon(Icons.Filled.Close, contentDescription = "Delete", tint = Color.Red)
                }
            }
            }
        }
    }
}