package com.surveasy.surveasy.presentation.intro

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.usecase.CreateNewPanelUseCase
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
        observeName()
        observeEmail()
        observePw()
        observePwCheck()
        observePhone()
        observeBirth()
        observeInflow()
        observeBank()
        observeAccount()
        observeAccountOwner()
    }

    fun navigateRegisterPages(type: RegisterEventType) {
        viewModelScope.launch {
            _events.emit(
                when (type) {
                    RegisterEventType.TO_EXIST_LOGIN -> RegisterEvents.NavigateToExistLogin
                    RegisterEventType.TO_AGREE -> RegisterEvents.NavigateToRegisterAgree
                    RegisterEventType.TO_WARN -> RegisterEvents.NavigateToRegisterWarn
                    RegisterEventType.TO_INPUT1 -> RegisterEvents.NavigateToRegisterInput1
                    RegisterEventType.TO_INPUT2 -> RegisterEvents.NavigateToRegisterInput2
                    RegisterEventType.TO_BACK -> RegisterEvents.NavigateToBack
                    RegisterEventType.TO_MAIN -> RegisterEvents.NavigateToMain
                }
            )
        }
    }

    private fun observeName() {
        name.onEach { name ->
            val isValid = name.length > NAME_LENGTH
            _uiState.update { state ->
                state.copy(
                    nameState = InputState(
                        helperText = if (name.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.NAME_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observeEmail() {
        email.onEach { email ->
            val isValid = email.matches(EMAIL_REGEX)
            _uiState.update { state ->
                state.copy(
                    emailState = InputState(
                        helperText = if (email.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.EMAIL_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observePw() {
        pw.onEach { pw ->
            val isValid = pw.length > PW_LENGTH
            _uiState.update { state ->
                state.copy(
                    pwState = InputState(
                        helperText = if (pw.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.PW_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observePwCheck() {
        pwCheck.onEach { pwCheck ->
            val isValid = pwCheck == pw.value
            _uiState.update { state ->
                state.copy(
                    pwCheckState = InputState(
                        helperText = if (pwCheck.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.PW_CHECK_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun observePhone() {
        phone.onEach { phone ->
            val isValid = phone.matches(PHONE_REGEX)
            _uiState.update { state ->
                state.copy(
                    phoneState = InputState(
                        helperText = if (phone.isEmpty()) HelperText.NONE else if (isValid) HelperText.VALID else HelperText.PHONE_INVALID,
                        isValid = isValid
                    )
                )
            }
        }.launchIn(viewModelScope)
    }

    fun setBirth(date: String) {
        viewModelScope.launch { birth.emit(date) }
    }

    private fun observeBirth() {
        birth.onEach { birth ->
            val isValid = birth.matches(BIRTH_REGEX)
            _uiState.update { state ->
                state.copy(
                    birthState = InputState(isValid = isValid)
                )
            }
        }.launchIn(viewModelScope)
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
            Log.d("TEST", "$isValid, $accountOwner")
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

    fun createNewPanel(){
        newPanelUseCase(
            name = name.value,
            email = email.value,
            fcmToken = "temp",
            gender = "여",
            birth = birth.value,
            accountOwner = accountOwner.value,
            accountType = bank.value,
            accountNumber = account.value,
            inflowPath = inflow.value,
            inflowPathEtc = "temp",
            phoneNumber = phone.value,
            platform = "ANDROID",
            pushOn = false,
            marketing = agreeMarketing.value
        ).onEach {

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
        const val PW_LENGTH = 7
        val EMAIL_REGEX =
            Regex("""^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})${'$'}""")
        val PHONE_REGEX = Regex("""^[0-9]{11}${'$'}""")
        val BIRTH_REGEX = Regex("""^\d{4}/\d{2}/\d{2}${'$'}""")
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
    data object NavigateToExistLogin : RegisterEvents()
    data object NavigateToRegisterAgree : RegisterEvents()
    data object NavigateToRegisterWarn : RegisterEvents()
    data object NavigateToRegisterInput1 : RegisterEvents()
    data object NavigateToRegisterInput2 : RegisterEvents()
    data object NavigateToBack : RegisterEvents()
    data object NavigateToMain : RegisterEvents()
}