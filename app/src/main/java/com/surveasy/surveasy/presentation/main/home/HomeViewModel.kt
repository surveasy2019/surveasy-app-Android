package com.surveasy.surveasy.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.ListHomeSurveyUseCase
import com.surveasy.surveasy.domain.usecase.QueryPanelInfoUseCase
import com.surveasy.surveasy.presentation.main.home.mapper.toUiHomeListData
import com.surveasy.surveasy.presentation.main.home.mapper.toUiPanelData
import com.surveasy.surveasy.presentation.main.home.model.UiHomeListData
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
class HomeViewModel @Inject constructor(
    private val queryPanelInfoUseCase: QueryPanelInfoUseCase,
    private val listHomeSurveyUseCase: ListHomeSurveyUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<HomeEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<HomeEvents> = _events.asSharedFlow()


    fun listHomeSurvey() {
        listHomeSurveyUseCase().onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.let { survey ->
                        _uiState.update {
                            val data =
                                survey.surveyAppHomeList.map { list -> list.toUiHomeListData() }
                            it.copy(list = data)
                        }
                    }
                }

                else -> Log.d("TEST", "failed")
            }
        }.launchIn(viewModelScope)
    }

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

    fun navigateToSurveyDetail(id: Int) {
        viewModelScope.launch { _events.emit(HomeEvents.ClickSurveyItem(id)) }
    }

}

sealed class HomeEvents {
    data class ClickSurveyItem(val id: Int) : HomeEvents()
}

data class HomeUiState(
    val name: String = "",
    val count: Int = 0,
    val rewardCurrent: Int = 0,
    val rewardTotal: Int = 0,
    val list: List<UiHomeListData> = emptyList(),
)