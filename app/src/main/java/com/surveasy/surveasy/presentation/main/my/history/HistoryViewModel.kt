package com.surveasy.surveasy.presentation.main.my.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.ListHistoryUseCase
import com.surveasy.surveasy.presentation.main.list.ListEvents
import com.surveasy.surveasy.presentation.main.my.history.mapper.toUiHistorySurveyData
import com.surveasy.surveasy.presentation.main.my.history.model.UiHistorySurveyData
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
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val listHistoryUseCase: ListHistoryUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<ListEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<ListEvents> = _events.asSharedFlow()

    fun listHistory(type: String) {
        listHistoryUseCase(type).onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.let { history ->
                        _uiState.update {
                            val data =
                                history.responseList.map { list -> list.toUiHistorySurveyData() }
                            it.copy(
                                list = data
                            )
                        }
                    }
                }

                else -> Log.d("TEST", "failed")
            }
        }.launchIn(viewModelScope)

    }


}

data class HistoryUiState(
    val reward: Int = 0,
    val account: String = "",
    val owner: String = "",
    val pageNum: Int = 0,
    val pageSize: Int = 0,
    val totalElements: Int = 0,
    val totalPages: Int = 0,
    val list: List<UiHistorySurveyData> = emptyList()
)