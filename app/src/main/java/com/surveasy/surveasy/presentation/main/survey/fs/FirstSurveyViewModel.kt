package com.surveasy.surveasy.presentation.main.survey.fs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstSurveyViewModel @Inject constructor() : ViewModel() {

//    private val _uiState = MutableStateFlow(SurveyUiState())
//    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<FsEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<FsEvents> = _events.asSharedFlow()

    fun navigateToNext(type: FsNavType) {
        viewModelScope.launch {
            when (type) {
                FsNavType.TO_INPUT2 -> _events.emit(FsEvents.NavigateToInput2)
                FsNavType.TO_DONE -> _events.emit(FsEvents.NavigateToDone)
                FsNavType.TO_BACK -> _events.emit(FsEvents.NavigateToBack)
            }
        }
    }
}

sealed class FsEvents {
    data object NavigateToInput2 : FsEvents()
    data object NavigateToDone : FsEvents()
    data object NavigateToBack : FsEvents()
}

enum class FsNavType {
    TO_INPUT2,
    TO_DONE,
    TO_BACK,
}