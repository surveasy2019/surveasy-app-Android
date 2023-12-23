package com.surveasy.surveasy.presentation.main.my.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.QueryPanelDetailInfoUseCase
import com.surveasy.surveasy.presentation.main.my.mapper.toUiPanelDetailData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyEditViewModel @Inject constructor(
    private val queryPanelDetailInfoUseCase: QueryPanelDetailInfoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyEditUiState())
    val uiState: StateFlow<MyEditUiState> = _uiState.asStateFlow()

//    private val _events = MutableSharedFlow<MyEditUiState>(
//        replay = 0,
//        extraBufferCapacity = 1,
//        onBufferOverflow = BufferOverflow.DROP_OLDEST
//    )
//    val events: SharedFlow<MyUiEvents> = _events.asSharedFlow()

    fun queryPanelDetailInfo() {
        queryPanelDetailInfoUseCase().onEach { state ->
            when (state) {
                is BaseState.Success -> {
                    state.data.toUiPanelDetailData().apply {
                        _uiState.update { info ->
                            info.copy(
                                name = name,
                                birth = birth,
                                gender = gender,
                                email = email,
                                phoneNumber = phoneNumber,
                                accountOwner = accountOwner,
                                accountType = accountType,
                                accountNumber = accountNumber,
                                english = english
                            )
                        }
                    }
                }

                is BaseState.Error -> Unit
            }
        }.launchIn(viewModelScope)
    }
}

data class MyEditUiState(
    val name: String = "",
    val birth: String = "",
    val gender: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val accountOwner: String = "",
    val accountType: String = "",
    val accountNumber: String = "",
    val english: Boolean = false
)