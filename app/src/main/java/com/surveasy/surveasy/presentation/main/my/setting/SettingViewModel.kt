package com.surveasy.surveasy.presentation.main.my.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.SignoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val signoutUseCase: SignoutUseCase,
) : ViewModel() {
    private val _events = MutableSharedFlow<SettingUiEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<SettingUiEvents> = _events.asSharedFlow()

    fun logout() {
        signoutUseCase().onEach {
            when (it) {
                is BaseState.Success -> {
                    dataStoreManager.deleteAccessToken()
                    dataStoreManager.deleteRefreshToken()
                    dataStoreManager.deleteAutoLogin()
                    _events.emit(SettingUiEvents.Logout)
                }

                else -> _events.emit(SettingUiEvents.ShowSnackBar("로그아웃에 실패했습니다. 다시 시도해주세요."))
            }
        }.launchIn(viewModelScope)
    }

    fun navigateToWithdraw() {
        viewModelScope.launch { _events.emit(SettingUiEvents.Withdraw) }
    }
}

sealed class SettingUiEvents {
    data object Logout : SettingUiEvents()
    data object Withdraw : SettingUiEvents()
    data class ShowToastMsg(val msg: String) : SettingUiEvents()
    data class ShowSnackBar(val msg: String) : SettingUiEvents()
}