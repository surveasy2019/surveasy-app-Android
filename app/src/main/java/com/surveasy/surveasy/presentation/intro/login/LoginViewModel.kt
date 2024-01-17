package com.surveasy.surveasy.presentation.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.model.Gender
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.CreateExistPanelUseCase
import com.surveasy.surveasy.domain.usecase.KakaoSignupUseCase
import com.surveasy.surveasy.presentation.util.ErrorMsg.GET_INFO_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.SIGNUP_ERROR
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
class LoginViewModel @Inject constructor(
    private val createExistPanelUseCase: CreateExistPanelUseCase,
    private val kakaoSignupUseCase: KakaoSignupUseCase,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    val email = MutableStateFlow("")
    val pw = MutableStateFlow("")

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvents>(
        replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1
    )
    val events: SharedFlow<LoginEvents> = _events.asSharedFlow()

    init {
        observeEmail()
        observePw()
    }

    fun startKakaoSignup() {
        viewModelScope.launch { _events.emit(LoginEvents.ClickKakaoSignup) }
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

    fun kakaoSignup(
        name: String?,
        email: String?,
        phone: String?,
        gender: Gender?,
        birthYear: String?,
        birth: String?,
        provider: String = "KAKAO"
    ) {
        if (name == null || email == null || phone == null || gender == null || birthYear == null || birth == null) {
            viewModelScope.launch { _events.emit(LoginEvents.ShowSnackBar(GET_INFO_ERROR)) }
        } else {
            kakaoSignupUseCase(
                name,
                email,
                setPhoneFormat(phone),
                gender.toString(),
                setBirthFormat(birthYear, birth),
                provider
            ).onEach {
                when (it) {
                    is BaseState.Success -> {
                        if (it.data.additionalInfo) {
                            dataStoreManager.putAccessToken(it.data.tokens.accessToken)
                            dataStoreManager.putRefreshToken(it.data.tokens.refreshToken)
                            _events.emit(LoginEvents.NavigateToMain)
                        } else {
                            dataStoreManager.putAccessToken(it.data.tokens.accessToken)
                            _events.emit(LoginEvents.NavigateToRegister)
                        }
                    }

                    else -> _events.emit(LoginEvents.ShowSnackBar(SIGNUP_ERROR))

                }
            }.launchIn(viewModelScope)
        }
    }

    private fun setBirthFormat(year: String, day: String): String {
        val formatDay =
            if (day.length == 4) "${day.substring(0, 2)}-${day.substring(2)}" else "01-01"
        return "${year}-$formatDay"
    }

    private fun setPhoneFormat(phone: String): String {
        val list = phone.split(" ")
        val format = list[list.size - 1]
        return "0${format.replace("-", "")}"
    }

}

data class LoginUiState(
    val email: String = "",
    val pw: String = "",
    val valid: Boolean = false,
)

sealed class LoginEvents {
    data object ClickKakaoSignup : LoginEvents()
    data object NavigateToRegister : LoginEvents()
    data object NavigateToMain : LoginEvents()
    data class ShowToastMsg(val msg: String) : LoginEvents()
    data class ShowSnackBar(val msg: String) : LoginEvents()
}