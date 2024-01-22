package com.surveasy.surveasy.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _events = MutableSharedFlow<MainEvents>()
    val events: SharedFlow<MainEvents> = _events.asSharedFlow()

    fun finishApp() = viewModelScope.launch {
        _events.emit(MainEvents.FinishApp)
    }
}

sealed class MainEvents {
    data object FinishApp : MainEvents()
}