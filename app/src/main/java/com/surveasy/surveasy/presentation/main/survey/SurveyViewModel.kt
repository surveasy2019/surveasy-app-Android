package com.surveasy.surveasy.presentation.main.survey

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
class SurveyViewModel @Inject constructor() : ViewModel() {
    private val _events = MutableSharedFlow<SurveyEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<SurveyEvents> = _events.asSharedFlow()

    fun navigateToSurvey() {
        viewModelScope.launch { _events.emit(SurveyEvents.NavigateToSurvey) }
    }

    fun navigateToProof() {
        viewModelScope.launch { _events.emit(SurveyEvents.NavigateToProof) }
    }

    fun navigateToDone() {
        viewModelScope.launch { _events.emit(SurveyEvents.NavigateToDone) }
    }

    fun navigateToMy() {
        viewModelScope.launch { _events.emit(SurveyEvents.NavigateToMy) }
    }

    fun navigateToList() {
        viewModelScope.launch { _events.emit(SurveyEvents.NavigateToList) }
    }

}

sealed class SurveyEvents {
    data object NavigateToSurvey : SurveyEvents()
    data object NavigateToProof : SurveyEvents()
    data object NavigateToDone : SurveyEvents()
    data object NavigateToMy : SurveyEvents()
    data object NavigateToList : SurveyEvents()
}