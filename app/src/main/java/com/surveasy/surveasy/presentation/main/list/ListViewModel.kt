package com.surveasy.surveasy.presentation.main.list

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
class ListViewModel @Inject constructor() : ViewModel() {
    private val _events = MutableSharedFlow<ListEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<ListEvents> = _events.asSharedFlow()

    fun navigateToSurveyDetail() {
        viewModelScope.launch { _events.emit(ListEvents.ClickSurveyItem(0)) }
    }
}

sealed class ListEvents {
    data class ClickSurveyItem(val id: Int) : ListEvents()
}