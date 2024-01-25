package com.surveasy.surveasy.presentation.main.my.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.EditResponseUseCase
import com.surveasy.surveasy.domain.usecase.ListHistoryUseCase
import com.surveasy.surveasy.domain.usecase.LoadImageUseCase
import com.surveasy.surveasy.domain.usecase.QueryAccountInfoUseCase
import com.surveasy.surveasy.presentation.main.my.history.mapper.toUiAccountData
import com.surveasy.surveasy.presentation.main.my.history.mapper.toUiHistorySurveyData
import com.surveasy.surveasy.presentation.main.my.history.model.UiHistorySurveyData
import com.surveasy.surveasy.presentation.util.ErrorCode
import com.surveasy.surveasy.presentation.util.ErrorMsg
import com.surveasy.surveasy.presentation.util.ErrorMsg.DATA_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.SURVEY_EDIT_DONE_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.SURVEY_EDIT_ERROR
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
import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val listHistoryUseCase: ListHistoryUseCase,
    private val editResponseUseCase: EditResponseUseCase,
    private val loadImageUseCase: LoadImageUseCase,
    private val queryAccountInfoUseCase: QueryAccountInfoUseCase,
) : ViewModel() {
    private val sid = MutableStateFlow(-1)
    val date = MutableStateFlow(0)

    private val _mainUiState = MutableStateFlow(HistoryUiState())
    val mainUiState: StateFlow<HistoryUiState> = _mainUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow(HistoryDetailUiState())
    val detailUiState: StateFlow<HistoryDetailUiState> = _detailUiState.asStateFlow()

    private val _events = MutableSharedFlow<HistoryEvents>(
        replay = 0,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<HistoryEvents> = _events.asSharedFlow()

    init {
        setDate()
    }

    fun queryAccountInfo() {
        queryAccountInfoUseCase().onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    _mainUiState.update {
                        val data = state.data.toUiAccountData()
                        it.copy(
                            reward = data.reward,
                            account = data.account,
                            owner = data.owner,
                            bank = data.bank
                        )
                    }
                }

                else -> _events.emit(HistoryEvents.ShowSnackBar(DATA_ERROR))
            }
        }.launchIn(viewModelScope)
    }

    fun listHistory(isBefore: Boolean) {
        val type = if (isBefore) BEFORE else AFTER
        _mainUiState.update { it.copy(isBefore = isBefore) }
        listHistoryUseCase(
            type = type,
            page = FIRST_PAGE,
            size = DEFAULT_SIZE,
            sort = null
        ).onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.let { history ->
                        _mainUiState.update {
                            val totalPage = history.pageInfo.totalPages
                            val data =
                                history.responseList.map { list -> list.toUiHistorySurveyData() }
                            it.copy(
                                isBefore = isBefore,
                                list = data,
                                lastPage = totalPage == 1,
                                nowPage = 1,
                                isListEmpty = data.isEmpty()
                            )
                        }
                    }
                }

                else -> _events.emit(HistoryEvents.ShowSnackBar(DATA_ERROR))
            }
        }.launchIn(viewModelScope)
    }

    fun loadNextPage() {
        val type = if (mainUiState.value.isBefore) BEFORE else AFTER
        _mainUiState.update { it.copy(isLoading = true) }
        listHistoryUseCase(
            type = type,
            page = mainUiState.value.nowPage,
            size = DEFAULT_SIZE,
            sort = null
        ).onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.let { list ->
                        _mainUiState.update {
                            val totalPage = list.pageInfo.totalPages
                            val data = mainUiState.value.list.toMutableList().apply {
                                addAll(list.responseList.map { list -> list.toUiHistorySurveyData() })
                            }
                            it.copy(
                                list = data,
                                nowPage = mainUiState.value.nowPage + 1,
                                lastPage = totalPage - 1 == mainUiState.value.nowPage,
                                isLoading = false
                            )
                        }
                    }
                }

                else -> _events.emit(HistoryEvents.ShowSnackBar(DATA_ERROR))
            }
        }.launchIn(viewModelScope)
    }

    fun getHistoryDetail() {
        mainUiState.value.list.find { it.id == sid.value }.apply {
            this ?: return@apply
            _detailUiState.update { state ->
                state.copy(
                    title = title,
                    reward = reward,
                    imgUrl = imgUrl,
                    createdAt = createdAt,
                    sentAt = sentAt
                )
            }
        }
    }

    suspend fun editResponse(uri: String, name: String) {
        _events.emit(HistoryEvents.ShowLoading)
        val imgUrl = viewModelScope.async {
            loadImageUseCase(uri, sid.value, name)
        }.await()

        if (imgUrl.isEmpty()) {
            _events.emit(HistoryEvents.DismissLoading)
            _events.emit(HistoryEvents.ShowSnackBar(ErrorMsg.SURVEY_ERROR))
        } else {
            editResponseUseCase(sid.value, imgUrl).onEach { state ->
                when (state) {
                    is BaseState.Success -> {
                        sid.emit(state.data.id)
                        _events.emit(HistoryEvents.ShowToastMsg("제출화면이 변경되었습니다."))
                        _events.emit(HistoryEvents.NavigateToHistoryMain)
                    }

                    is BaseState.Error -> {
                        _events.emit(
                            when (state.code) {
                                ErrorCode.CODE_400 -> HistoryEvents.ShowSnackBar(
                                    SURVEY_EDIT_DONE_ERROR
                                )

                                else -> HistoryEvents.ShowSnackBar(SURVEY_EDIT_ERROR)
                            }
                        )
                    }
                }
            }.onCompletion {
                _events.emit(HistoryEvents.DismissLoading)
            }.launchIn(viewModelScope)
        }

    }

    private fun setDate() {
        val seoulTimeZone: TimeZone = TimeZone.getTimeZone("Asia/Seoul")
        val calendar: Calendar = Calendar.getInstance(seoulTimeZone)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        viewModelScope.launch {
            date.emit(
                if (day > 30 || day <= 10) {
                    10
                } else if (day <= 20) {
                    20
                } else {
                    30
                }
            )
        }
    }


    fun navigateToDetail(id: Int) = viewModelScope.launch {
        _events.emit(HistoryEvents.NavigateToDetail)
        sid.emit(id)
    }

    fun navigateToEdit() = viewModelScope.launch { _events.emit(HistoryEvents.NavigateToEdit) }

    fun navigateToInfoEdit() =
        viewModelScope.launch { _events.emit(HistoryEvents.NavigateToInfoEdit) }

    fun navigateToBack() = viewModelScope.launch { _events.emit(HistoryEvents.NavigateToBack) }

    companion object {
        const val BEFORE = "before"
        const val AFTER = "after"
        const val FIRST_PAGE = 0
        const val DEFAULT_SIZE = 10
    }

}

sealed class HistoryEvents {
    data object NavigateToDetail : HistoryEvents()
    data object NavigateToEdit : HistoryEvents()
    data object NavigateToHistoryMain : HistoryEvents()
    data object NavigateToInfoEdit : HistoryEvents()
    data object NavigateToBack : HistoryEvents()
    data object ShowLoading : HistoryEvents()
    data object DismissLoading : HistoryEvents()
    data class ShowToastMsg(val msg: String) : HistoryEvents()
    data class ShowSnackBar(val msg: String) : HistoryEvents()
}

data class HistoryUiState(
    val reward: Int = 0,
    val account: String = "",
    val owner: String = "",
    val bank: String = "",
    val nowPage: Int = 0,
    val lastPage: Boolean = false,
    val isLoading: Boolean = false,
    val isBefore: Boolean = true,
    val list: List<UiHistorySurveyData> = emptyList(),
    val isListEmpty: Boolean = false
)

data class HistoryDetailUiState(
    val title: String = "",
    val reward: Int = 0,
    val imgUrl: String = "",
    val createdAt: String = "",
    val sentAt: String? = null,
)