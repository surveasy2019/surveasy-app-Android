package com.surveasy.surveasy.presentation.main.survey

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.QuerySurveyDetailUseCase
import com.surveasy.surveasy.presentation.main.list.mapper.toSurveyDetailData
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
class SurveyViewModel @Inject constructor(
    private val querySurveyDetailUseCase: QuerySurveyDetailUseCase,
) : ViewModel() {
    private val sId = MutableStateFlow(0)

    private val _uiState = MutableStateFlow(SurveyUiState())
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SurveyEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<SurveyEvents> = _events.asSharedFlow()

    fun querySurveyDetail() {
        querySurveyDetailUseCase(sid = sId.value).onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.toSurveyDetailData().apply {
                        _uiState.update { survey ->
                            survey.copy(
                                title = title,
                                reward = reward,
                                spendTime = spendTime,
                                responseCount = responseCount,
                                targetInput = targetInput,
                                noticeToPanel = noticeToPanel,
                                surveyDescription = ""
                            )
                        }
                    }
                }

                else -> Log.d("TEST", "failed")
            }
        }.launchIn(viewModelScope)
    }

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

    fun setSurveyId(sid: Int) {
        viewModelScope.launch { sId.emit(sid) }
    }

}

sealed class SurveyEvents {
    data object NavigateToSurvey : SurveyEvents()
    data object NavigateToProof : SurveyEvents()
    data object NavigateToDone : SurveyEvents()
    data object NavigateToMy : SurveyEvents()
    data object NavigateToList : SurveyEvents()
}

data class SurveyUiState(
    val title: String = "",
    val reward: Int = 0,
    val spendTime: String = "",
    val responseCount: Int = 0,
    val targetInput: String = "",
    val noticeToPanel: String = "",
    val surveyDescription: String = "",
)