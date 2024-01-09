package com.surveasy.surveasy.presentation.main.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.QueryPanelInfoUseCase
import com.surveasy.surveasy.presentation.main.home.mapper.toUiPanelData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val queryPanelInfoUseCase: QueryPanelInfoUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MyUiEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<MyUiEvents> = _events.asSharedFlow()

    fun queryPanelInfo() {
        queryPanelInfoUseCase().onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.toUiPanelData().apply {
                        _uiState.update { panel ->
                            panel.copy(
                                name = name,
                                count = count,
                                rewardCurrent = rewardCurrent,
                                rewardTotal = rewardTotal
                            )
                        }
                    }
                }

                else -> Unit
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToHistory() {
        viewModelScope.launch { _events.emit(MyUiEvents.NavigateToHistory) }
    }

    fun navigateToEdit() {
        viewModelScope.launch { _events.emit(MyUiEvents.NavigateToEdit) }
    }

    fun navigateToSetting() {
        viewModelScope.launch { _events.emit(MyUiEvents.NavigateToSetting) }
    }

    fun navigateToContact() {
        viewModelScope.launch { _events.emit(MyUiEvents.NavigateToContact) }
    }

}

data class MyUiState(
    val name: String = "",
    val count: Int = 0,
    val rewardCurrent: Int = 0,
    val rewardTotal: Int = 0,
)

sealed class MyUiEvents {
    data object NavigateToHistory : MyUiEvents()
    data object NavigateToEdit : MyUiEvents()
    data object NavigateToSetting : MyUiEvents()
    data object NavigateToContact : MyUiEvents()
}