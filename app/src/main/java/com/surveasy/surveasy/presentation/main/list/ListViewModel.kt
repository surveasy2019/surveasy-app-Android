package com.surveasy.surveasy.presentation.main.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.ListSurveyUseCase
import com.surveasy.surveasy.presentation.main.list.mapper.toUiSurveyListData
import com.surveasy.surveasy.presentation.main.list.model.UiSurveyListData
import com.surveasy.surveasy.presentation.util.ErrorMsg.DATA_ERROR
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
class ListViewModel @Inject constructor(
    private val listSurveyUseCase: ListSurveyUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SurveyListUiState())
    val uiState: StateFlow<SurveyListUiState> = _uiState.asStateFlow()
    private val _events = MutableSharedFlow<ListEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<ListEvents> = _events.asSharedFlow()

    fun listSurvey() {
        listSurveyUseCase().onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.let { survey ->
                        _uiState.update {
                            val didFs = survey.didFirstSurvey
                            val data =
                                survey.surveyAppList.map { list -> list.toUiSurveyListData() }
                            it.copy(
                                didFirstSurvey = didFs,
                                list = data
                            )
                        }
                    }
                }

                else -> _events.emit(ListEvents.ShowSnackBar(DATA_ERROR))
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToFs() = viewModelScope.launch { _events.emit(ListEvents.NavigateToFs) }

    fun navigateToSurveyDetail(id: Int) =
        viewModelScope.launch { _events.emit(ListEvents.ClickSurveyItem(id)) }

}

sealed class ListEvents {
    data class ClickSurveyItem(val id: Int) : ListEvents()
    data object NavigateToFs : ListEvents()
    data class ShowToastMsg(val msg: String) : ListEvents()
    data class ShowSnackBar(val msg: String) : ListEvents()
}

data class SurveyListUiState(
    val didFirstSurvey: Boolean = true,
    val list: List<UiSurveyListData> = emptyList()
)