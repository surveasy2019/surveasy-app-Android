package com.surveasy.surveasy.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.CreateNewPanelUseCase
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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val newPanelUseCase: CreateNewPanelUseCase,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {
    val agreeMust = MutableStateFlow(false)
    val agreeMarketing = MutableStateFlow(false)

    val showEtc = MutableStateFlow(false)
    private val inflow = MutableStateFlow("")
    val inflowEtc = MutableStateFlow("")
    private val bank = MutableStateFlow("")
    val account = MutableStateFlow("")
    val accountOwner = MutableStateFlow("")
    val push = MutableStateFlow(true)

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegisterEvents>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )
    val events: SharedFlow<RegisterEvents> = _events.asSharedFlow()

    init {
        observeInflow()
        observeInflowEtc()
        observeBank()
        observeAccount()
        observeAccountOwner()
    }

    fun navigateRegisterPages(type: RegisterEventType) {
        viewModelScope.launch {
            _events.emit(
                when (type) {
                    RegisterEventType.TO_WARN -> RegisterEvents.NavigateToRegisterWarn
                    RegisterEventType.TO_TERM1 -> RegisterEvents.NavigateToTerm1
                    RegisterEventType.TO_TERM2 -> RegisterEvents.NavigateToTerm2
                    RegisterEventType.TO_INPUT -> RegisterEvents.NavigateToRegisterInput
                    RegisterEventType.TO_BACK -> RegisterEvents.NavigateToBack
                    RegisterEventType.TO_DONE -> RegisterEvents.NavigateToDone
                    RegisterEventType.TO_MAIN -> RegisterEvents.NavigateToMain
                }
            )
        }
    }

    fun setInflow(select: String) {
        viewModelScope.launch { inflow.emit(select) }
    }

    private fun observeInflow() {
        inflow.onEach { inflow ->
            val isValid = inflow != INFLOW_DEFAULT
            showEtc.emit(inflow == INFLOW_ETC)
            _uiState.update { state ->
                state.copy(
                    inflowState = InputState(isValid = isValid)
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observeInflowEtc() {
        inflowEtc.onEach { etc ->
            val isValid = etc.length > NAME_LENGTH
            _uiState.update { state ->
                state.copy(
                    inflowEtcState = InputState(
                        helperText = if (etc.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.INFLOW_ETC_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    fun setBank(select: String) {
        viewModelScope.launch { bank.emit(select) }
    }

    fun observePush(isChecked: Boolean) {
        push.value = isChecked
    }

    private fun observeBank() {
        bank.onEach { bank ->
            val isValid = bank != BANK_DEFAULT
            _uiState.update { state ->
                state.copy(
                    bankState = InputState(isValid = isValid)
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observeAccount() {
        account.onEach { account ->
            val isValid = account.matches(ACCOUNT_REGEX)
            _uiState.update { state ->
                state.copy(
                    accountState = InputState(
                        helperText = if (account.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.ACCOUNT_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observeAccountOwner() {
        accountOwner.onEach { accountOwner ->
            val isValid = accountOwner.length > NAME_LENGTH
            _uiState.update { state ->
                state.copy(
                    accountOwnerState = InputState(
                        helperText = if (accountOwner.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.OWNER_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    fun createNewPanel() {
        newPanelUseCase(
            platform = ANDROID,
            accountOwner = accountOwner.value,
            accountType = bank.value,
            accountNumber = account.value,
            inflowPath = inflow.value,
            inflowPathEtc = inflowEtc.value.ifBlank { null },
            marketingAgree = agreeMarketing.value,
            pushOn = push.value
        ).onEach { register ->
            _events.emit(RegisterEvents.ShowLoading)
            when (register) {
                is BaseState.Success -> {
                    dataStoreManager.putAccessToken(register.data.accessToken)
                    dataStoreManager.putRefreshToken(register.data.refreshToken)
                    dataStoreManager.putAutoLogin(true)
                    _events.emit(RegisterEvents.NavigateToDone)
                }

                is BaseState.Error -> _events.emit(RegisterEvents.ShowSnackBar(SIGNUP_ERROR))
            }
        }.onCompletion {
            _events.emit(RegisterEvents.DismissLoading)
        }.launchIn(viewModelScope)
    }


    fun checkMust(state: Boolean) {
        agreeMust.value = state
    }

    fun checkMarketing(state: Boolean) {
        agreeMarketing.value = state
    }


    companion object {
        const val NAME_LENGTH = 1
        val ACCOUNT_REGEX = Regex("\\d+")
        const val INFLOW_DEFAULT = "유입경로를 선택하세요"
        const val INFLOW_ETC = "기타"
        const val BANK_DEFAULT = "은행을 선택하세요"
        const val ANDROID = "ANDROID"
    }
}


data class RegisterUiState(
    val inflowState: InputState = InputState(),
    val bankState: InputState = InputState(),
    val accountState: InputState = InputState(),
    val accountOwnerState: InputState = InputState(),
    val inflowEtcState: InputState = InputState(),
)

data class InputState(
    val helperText: HelperText = HelperText.NONE,
    val isValid: Boolean = false
)

sealed class RegisterEvents {
    data object NavigateToRegisterWarn : RegisterEvents()
    data object NavigateToTerm1 : RegisterEvents()
    data object NavigateToTerm2 : RegisterEvents()
    data object NavigateToRegisterInput : RegisterEvents()
    data object NavigateToBack : RegisterEvents()
    data object NavigateToDone : RegisterEvents()
    data object NavigateToMain : RegisterEvents()
    data object ShowLoading : RegisterEvents()
    data object DismissLoading : RegisterEvents()
    data class ShowToastMsg(val msg: String) : RegisterEvents()
    data class ShowSnackBar(val msg: String) : RegisterEvents()
}