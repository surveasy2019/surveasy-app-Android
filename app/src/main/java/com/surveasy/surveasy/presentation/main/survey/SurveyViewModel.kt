package com.surveasy.surveasy.presentation.main.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.CreateResponseUseCase
import com.surveasy.surveasy.domain.usecase.LoadImageUseCase
import com.surveasy.surveasy.domain.usecase.QuerySurveyDetailUseCase
import com.surveasy.surveasy.presentation.main.survey.mapper.toSurveyDetailData
import com.surveasy.surveasy.presentation.util.ErrorMsg.DATA_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.SURVEY_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val querySurveyDetailUseCase: QuerySurveyDetailUseCase,
    private val loadImageUseCase: LoadImageUseCase,
    private val createResponseUseCase: CreateResponseUseCase,
) : ViewModel() {
    private val sId = MutableStateFlow(0)

    private val _uiState = MutableStateFlow(SurveyUiState())
    val uiState: StateFlow<SurveyUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<SurveyEvents>(
        replay = 0,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<SurveyEvents> = _events.asSharedFlow()


    suspend fun createResponse(uri: String, id: Int, name: String) {
        _events.emit(SurveyEvents.ShowLoading)
        val imgUrl = viewModelScope.async {
            loadImageUseCase(uri, id, name)
        }.await()

        if (imgUrl.isEmpty()) {
            _events.emit(SurveyEvents.DismissLoading)
            _events.emit(SurveyEvents.ShowSnackBar(SURVEY_ERROR))
        } else {
            createResponseUseCase(sId.value, imgUrl).onEach { state ->
                when (state) {
                    is BaseState.Success -> _events.emit(SurveyEvents.NavigateToDone)
                    else -> _events.emit(SurveyEvents.ShowSnackBar(SURVEY_ERROR))
                }
            }.onCompletion {
                _events.emit(SurveyEvents.DismissLoading)
            }.launchIn(viewModelScope)

        }

    }

    fun querySurveyDetail() {
        querySurveyDetailUseCase(sid = sId.value).onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.toSurveyDetailData().apply {
                        _uiState.update { survey ->
                            survey.copy(
                                title = title,
                                reward = reward,
                                headCount = headCount,
                                spendTime = spendTime,
                                responseCount = responseCount,
                                targetInput = targetInput,
                                noticeToPanel = noticeToPanel,
                                surveyDescription = "",
                                link = link
                            )
                        }
                    }
                }

                else -> _events.emit(SurveyEvents.ShowSnackBar(DATA_ERROR))
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
    data object ShowLoading : SurveyEvents()
    data object DismissLoading : SurveyEvents()
    data class ShowToastMsg(val msg: String) : SurveyEvents()
    data class ShowSnackBar(val msg: String) : SurveyEvents()
}

data class SurveyUiState(
    val title: String = "",
    val reward: Int = 0,
    val headCount: Int = 0,
    val spendTime: String = "",
    val responseCount: Int = 0,
    val targetInput: String = "",
    val noticeToPanel: String = "",
    val surveyDescription: String = "",
    val link: String = "",
)