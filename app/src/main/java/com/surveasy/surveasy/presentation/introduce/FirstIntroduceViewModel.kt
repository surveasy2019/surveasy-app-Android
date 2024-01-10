package com.surveasy.surveasy.presentation.introduce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.app.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstIntroduceViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _events = MutableSharedFlow<FirstIntroduceEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<FirstIntroduceEvents> = _events.asSharedFlow()

    fun navigateToLogin() {
        viewModelScope.launch {
            dataStoreManager.putTutorial()
            _events.emit(FirstIntroduceEvents.NavigateToLogin)
        }
    }
}

sealed class FirstIntroduceEvents {
    data object NavigateToLogin : FirstIntroduceEvents()
}
