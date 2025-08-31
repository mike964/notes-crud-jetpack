package com.example.tasklistviewmodel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterExample(
    viewModel: CounterViewModel = viewModel(), // Obtain ViewModel instance
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle() // Observe UI state

    Box(
        modifier = Modifier.fillMaxSize(), // Makes the Box fill the available space
        contentAlignment = Alignment.Center // Centers all children within the Box
    ){
        Card(
            modifier = Modifier.padding(16.dp) // Add some padding around the card
        ) {
            Column(
                modifier = Modifier.padding(16.dp) // Add padding inside the card
            ) {
                Text(text = "Count: ${uiState.count}")
                Row {
                    Button(onClick = { viewModel.decrementCount() } , colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF933333), // Background color when enabled
                        contentColor = Color.White,  // Content color when enabled
                    )) {
                        Text("- 1")
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = { viewModel.incrementCount() }) {
                        Text("+ 1")
                    }
                }
            }
        }
    }

}