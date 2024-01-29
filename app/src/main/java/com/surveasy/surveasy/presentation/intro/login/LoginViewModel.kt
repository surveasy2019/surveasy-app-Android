package com.surveasy.surveasy.presentation.intro.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.model.Gender
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.CreateExistPanelUseCase
import com.surveasy.surveasy.domain.usecase.GetFbUidUseCase
import com.surveasy.surveasy.domain.usecase.KakaoSignupUseCase
import com.surveasy.surveasy.presentation.util.ErrorCode.CODE_404
import com.surveasy.surveasy.presentation.util.ErrorMsg.EXIST_LOGIN_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.EXIST_LOGIN_ERROR_TWICE
import com.surveasy.surveasy.presentation.util.ErrorMsg.EXIST_LOGIN_NULL_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.GET_INFO_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.KAKAO_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.SIGNUP_ERROR
import com.surveasy.surveasy.presentation.util.ErrorMsg.UNKNOWN_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val createExistPanelUseCase: CreateExistPanelUseCase,
    private val kakaoSignupUseCase: KakaoSignupUseCase,
    private val dataStoreManager: DataStoreManager,
    private val getFbUidUseCase: GetFbUidUseCase,
) : ViewModel() {
    val email = MutableStateFlow("")
    val pw = MutableStateFlow("")
    private val autoLogin = MutableStateFlow(true)

    private var errorCount = 0

    private val _events = MutableSharedFlow<LoginEvents>(
        replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1
    )
    val events: SharedFlow<LoginEvents> = _events.asSharedFlow()

    fun setAutoLogin(state: Boolean) {
        autoLogin.value = state
    }

    suspend fun createExistPanel() {
        if (email.value.isBlank() || pw.value.isBlank()) {
            _events.emit(LoginEvents.ShowSnackBar(EXIST_LOGIN_NULL_ERROR))
            return
        }
        _events.emit(LoginEvents.ShowLoading)
        val fbUid = viewModelScope.async {
            getFbUidUseCase(email.value, pw.value)
        }.await()

        if (fbUid.isBlank()) {
            errorCount++
            _events.emit(LoginEvents.DismissLoading)
            _events.emit(LoginEvents.ShowSnackBar(if (errorCount > 2) EXIST_LOGIN_ERROR_TWICE else EXIST_LOGIN_ERROR))
        } else {
            createExistPanelUseCase(fbUid, email.value, pw.value).onEach { state ->
                when (state) {
                    is BaseState.Success -> {
                        dataStoreManager.putAutoLogin(autoLogin.value)
                        dataStoreManager.putAccessToken(state.data.accessToken)
                        dataStoreManager.putRefreshToken(state.data.refreshToken)
                        _events.emit(LoginEvents.NavigateToMain)
                    }

                    is BaseState.Error -> {
                        if (state.code == CODE_404) {
                            _events.emit(LoginEvents.ShowSnackBar(UNKNOWN_ERROR))
                        } else {
                            errorCount++
                            _events.emit(LoginEvents.ShowSnackBar(if (errorCount > 2) EXIST_LOGIN_ERROR_TWICE else EXIST_LOGIN_ERROR))
                        }
                    }
                }
            }.onCompletion {
                _events.emit(LoginEvents.DismissLoading)
            }.launchIn(viewModelScope)
        }
    }


    fun startKakaoSignup() {
        viewModelScope.launch { _events.emit(LoginEvents.ClickKakaoSignup) }
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
        viewModelScope.launch { _events.emit(LoginEvents.ShowLoading) }
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
                            dataStoreManager.putAutoLogin(true)
                            _events.emit(LoginEvents.NavigateToMain)
                        } else {
                            dataStoreManager.putAccessToken(it.data.tokens.accessToken)
                            _events.emit(LoginEvents.NavigateToRegister)
                        }
                    }

                    else -> _events.emit(LoginEvents.ShowSnackBar(KAKAO_ERROR))

                }
            }.onCompletion {
                _events.emit(LoginEvents.DismissLoading)
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

sealed class LoginEvents {
    data object ClickKakaoSignup : LoginEvents()
    data object NavigateToRegister : LoginEvents()
    data object NavigateToMain : LoginEvents()
    data object ShowLoading : LoginEvents()
    data object DismissLoading : LoginEvents()
    data class ShowToastMsg(val msg: String) : LoginEvents()
    data class ShowSnackBar(val msg: String) : LoginEvents()
}