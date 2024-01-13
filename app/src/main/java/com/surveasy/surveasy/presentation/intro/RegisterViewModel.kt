package com.surveasy.surveasy.presentation.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val newPanelUseCase: CreateNewPanelUseCase
) : ViewModel() {
    val agreeAll = MutableStateFlow(false)
    val agreeMust1 = MutableStateFlow(false)
    val agreeMust2 = MutableStateFlow(false)
    val agreeMarketing = MutableStateFlow(false)
    val name = MutableStateFlow("")
    val email = MutableStateFlow("")
    val pw = MutableStateFlow("")
    val pwCheck = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val gender = MutableStateFlow(true)
    val birth = MutableStateFlow("생년월일을 선택해주세요.")
    val inflow = MutableStateFlow("")
    val bank = MutableStateFlow("")
    val account = MutableStateFlow("")
    val accountOwner = MutableStateFlow("")

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<RegisterEvents>(
        replay = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1
    )
    val events: SharedFlow<RegisterEvents> = _events.asSharedFlow()

    init {
        checkAgreeAll()
        checkMust1()
        checkMust2()
        checkMarketing()
        observeInflow()
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
            _uiState.update { state ->
                state.copy(
                    inflowState = InputState(isValid = isValid)
                )
            }
        }.launchIn(viewModelScope)
    }

    fun setBank(select: String) {
        viewModelScope.launch { bank.emit(select) }
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

    fun setGender(isMale: Boolean) {
        viewModelScope.launch { gender.emit(isMale) }
    }

    fun createNewPanel() {
        newPanelUseCase(
            name = name.value,
            email = email.value,
            fcmToken = "temp",
            gender = gender.value,
            birth = birth.value,
            accountOwner = accountOwner.value,
            accountType = bank.value,
            accountNumber = account.value,
            inflowPath = inflow.value,
            inflowPathEtc = "",
            phoneNumber = phone.value,
            platform = "ANDROID",
            pushOn = false,
            marketing = agreeMarketing.value
        ).onEach { register ->
            when (register) {
                is BaseState.Success -> _events.emit(RegisterEvents.NavigateToMain)
                is BaseState.Error -> _events.emit(RegisterEvents.ShowSnackBar(SIGNUP_ERROR))
            }
        }.launchIn(viewModelScope)
    }


    // all click logic 추가
    private fun checkAgreeAll() {
        agreeAll.onEach { all ->
            if (all) {
                agreeMust1.emit(true)
                agreeMust2.emit(true)
                agreeMarketing.emit(true)
            }

        }.launchIn(viewModelScope)
    }

    private fun checkMust1() {
        agreeMust1.onEach { check ->
            agreeMust1.emit(check)
            agreeAll.emit(
                isAllChecked()
            )
        }.launchIn(viewModelScope)
    }

    private fun checkMust2() {
        agreeMust2.onEach { check ->
            agreeMust2.emit(check)
            agreeAll.emit(
                isAllChecked()
            )
        }.launchIn(viewModelScope)
    }

    private fun checkMarketing() {
        agreeMarketing.onEach { check ->
            agreeMarketing.emit(check)
            agreeAll.emit(
                isAllChecked()
            )
        }.launchIn(viewModelScope)
    }

    private fun isAllChecked() = agreeMust1.value && agreeMust2.value && agreeMarketing.value

    companion object {
        const val NAME_LENGTH = 1
        val ACCOUNT_REGEX = Regex("\\d+")
        const val INFLOW_DEFAULT = "유입경로를 선택하세요"
        const val BANK_DEFAULT = "은행을 선택하세요"
    }
}


data class RegisterUiState(
    val nameState: InputState = InputState(),
    val emailState: InputState = InputState(),
    val pwState: InputState = InputState(),
    val pwCheckState: InputState = InputState(),
    val phoneState: InputState = InputState(),
    val birthState: InputState = InputState(),
    val inflowState: InputState = InputState(),
    val bankState: InputState = InputState(),
    val accountState: InputState = InputState(),
    val accountOwnerState: InputState = InputState(),
)

data class InputState(
    val helperText: HelperText = HelperText.NONE, val isValid: Boolean = false
)

sealed class RegisterEvents {
    data object NavigateToRegisterWarn : RegisterEvents()
    data object NavigateToTerm1 : RegisterEvents()
    data object NavigateToTerm2 : RegisterEvents()
    data object NavigateToRegisterInput : RegisterEvents()
    data object NavigateToBack : RegisterEvents()
    data object NavigateToMain : RegisterEvents()
    data class ShowToastMsg(val msg: String) : RegisterEvents()
    data class ShowSnackBar(val msg: String) : RegisterEvents()
}