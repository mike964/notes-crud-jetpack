package com.example.tasklistviewmodel.notes

import java.time.LocalDateTime

data class Note(
    val id: String,
    val content: String,
    val isImportant: Boolean = false,
    val dateTime: LocalDateTime = LocalDateTime.now(),
)
