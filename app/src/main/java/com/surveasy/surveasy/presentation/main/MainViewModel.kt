package com.surveasy.surveasy.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.usecase.CheckVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkVersionUseCase: CheckVersionUseCase
) : ViewModel() {
    private val _events = MutableSharedFlow<MainEvents>()
    val events: SharedFlow<MainEvents> = _events.asSharedFlow()


    fun finishApp() = viewModelScope.launch {
        _events.emit(MainEvents.FinishApp)
    }

    suspend fun checkVersion(version: String) {
        val isUpdated = viewModelScope.async {
            checkVersionUseCase(version)
        }.await()

        if (!isUpdated) {
            _events.emit(MainEvents.UpdateDialog)
        }
    }
}

sealed class MainEvents {
    data object FinishApp : MainEvents()
    data object UpdateDialog : MainEvents()
}