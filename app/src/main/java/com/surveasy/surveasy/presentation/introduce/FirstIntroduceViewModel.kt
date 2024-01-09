package com.surveasy.surveasy.presentation.introduce

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FirstIntroduceViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FirstIntroduceUiState())
    val uiState: StateFlow<FirstIntroduceUiState> = _uiState.asStateFlow()

//    private val _events = MutableSharedFlow<HomeEvents>(
//        replay = 0,
//        extraBufferCapacity = 1,
//        onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//    val events: SharedFlow<HomeEvents> = _events.asSharedFlow()
//

}

data class FirstIntroduceUiState(
    val img: Int = 0,
    val title: Int = 0,
    val content: Int = 0,
    val isLast: Boolean = false,
)