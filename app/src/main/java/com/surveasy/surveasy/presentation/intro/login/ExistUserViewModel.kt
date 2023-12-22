package com.surveasy.surveasy.presentation.intro.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.CreateExistPanelUseCase
import com.surveasy.surveasy.domain.usecase.GetUidUseCase
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
class ExistUserViewModel @Inject constructor(
    private val getUidUseCase: GetUidUseCase,
    private val createExistPanelUseCase: CreateExistPanelUseCase
) : ViewModel() {
    val email = MutableStateFlow("")
    val pw = MutableStateFlow("")

    private val _uiState = MutableStateFlow(ExistUserUiState())
    val uiState: StateFlow<ExistUserUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ExistUserEvents>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )
    val events: SharedFlow<ExistUserEvents> = _events.asSharedFlow()

    init {
        observeEmail()
        observePw()
    }

    private fun observeEmail() {
        email.onEach {
            _uiState.update { state ->
                state.copy(
                    email = it
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observePw() {
        pw.onEach {
            _uiState.update { state ->
                state.copy(
                    pw = it
                )
            }
        }.launchIn(viewModelScope)
    }

    fun existLogin() {
        createExistPanelUseCase("test", email.value).onEach { register ->
            when (register) {
                is BaseState.Success -> {
                    _events.emit(ExistUserEvents.NavigateToMain)
                }

                is BaseState.Error -> Log.d("TEST", "register fail")
            }
        }.launchIn(viewModelScope)
//        getUidUseCase(email.value, pw.value).onEach { login ->
//            when(login){
//                is BaseState.Success -> {
//                    createExistPanelUseCase(login.data, email.value).onEach { register ->
//                        when(register) {
//                            is BaseState.Success -> {
//                                _events.emit(ExistUserEvents.NavigateToMain)
//                            }
//                            is BaseState.Error -> Log.d("TEST", "register fail")
//                        }
//                    }
//                }
//                is BaseState.Error -> Log.d("TEST", "login fail")
//            }
//        }.launchIn(viewModelScope)
    }

}

data class ExistUserUiState(
    val email: String = "",
    val pw: String = "",
    val valid: Boolean = false,
)

sealed class ExistUserEvents {
    data object NavigateToMain : ExistUserEvents()
}