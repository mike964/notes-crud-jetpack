package com.example.tasklistviewmodel

// Note: Depending on your exact library version, you may need a different import
// e.g., androidx.compose.material3.ExperimentalMaterial3ExpressiveApi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TextToggleButton(label: String = "", checked :Boolean,  onClick:()->Unit) {
    var checked by remember { mutableStateOf(checked) }

    Row(
        horizontalArrangement = Arrangement.Center, // Centers children horizontally
        verticalAlignment = Alignment.CenterVertically // Centers children vertically
    )
    {
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onClick()
            }
//            onCheckedChange = { onClick }
        )
        Text(text=label , fontWeight= FontWeight.Bold, modifier = Modifier.padding(4.dp, 0.dp))
    }
}