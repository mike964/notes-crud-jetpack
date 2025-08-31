package com.example.tasklistviewmodel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CounterUiState(val count: Int = 0)

class CounterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    fun incrementCount() {
        _uiState.update { currentState ->
            currentState.copy(count = currentState.count + 1)
        }
    }

    fun decrementCount() {
        _uiState.update { currentState ->
            currentState.copy(count = currentState.count - 1)
        }
    }
}