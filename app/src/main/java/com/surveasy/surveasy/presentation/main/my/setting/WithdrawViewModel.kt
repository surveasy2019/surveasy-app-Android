package com.surveasy.surveasy.presentation.main.my.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.WithdrawUseCase
import com.surveasy.surveasy.presentation.util.ErrorMsg.WITHDRAW_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val withdrawUseCase: WithdrawUseCase
) : ViewModel() {

    private val reason1 = MutableStateFlow(false)
    private val reason2 = MutableStateFlow(false)
    private val reason3 = MutableStateFlow(false)
    private val reason4 = MutableStateFlow(false)
    private val reason5 = MutableStateFlow(false)

    //    val isCheckReady = combine(reason1, reason2, reason3, reason4, reason5) { r1, r2, r3, r4, r5 ->
//        r1 || r2 || r3 || r4 || r5
//    }.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(),
//        false
//    )
    private val _events = MutableSharedFlow<WithdrawEvents>(
        replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1
    )
    val events: SharedFlow<WithdrawEvents> = _events.asSharedFlow()

    fun setReason1(state: Boolean) {
        reason1.value = state
    }

    fun setReason2(state: Boolean) {
        reason2.value = state
    }

    fun setReason3(state: Boolean) {
        reason3.value = state
    }

    fun setReason4(state: Boolean) {
        reason4.value = state
    }

    fun setReason5(state: Boolean) {
        reason5.value = state
    }

    fun withdraw() {
        withdrawUseCase().onEach {
            when (it) {
                is BaseState.Success -> {
                    if (it.data.authProvider == EMAIL) {
                        // fb withdraw
                    } else {
                        kakaoUnlink()
                    }
                    dataStoreManager.deleteAccessToken()
                    dataStoreManager.deleteRefreshToken()
                    dataStoreManager.deleteAutoLogin()
                    _events.emit(WithdrawEvents.Withdraw)
                }

                else -> {
                    _events.emit(WithdrawEvents.ShowSnackBar(WITHDRAW_ERROR))
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun kakaoUnlink() {
        UserApiClient.instance.unlink { }
    }

    companion object {
        const val EMAIL = "EMAIL"
        const val KAKAO = "KAKAO"
    }
}

sealed class WithdrawEvents {
    data object Withdraw : WithdrawEvents()
    data object NavigateToBack : WithdrawEvents()
    data class ShowSnackBar(val msg: String) : WithdrawEvents()
}