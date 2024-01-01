package com.surveasy.surveasy.presentation.main.my.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.usecase.QueryPanelDetailInfoUseCase
import com.surveasy.surveasy.presentation.main.my.mapper.toUiPanelDetailData
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
class MyEditViewModel @Inject constructor(
    private val queryPanelDetailInfoUseCase: QueryPanelDetailInfoUseCase
) : ViewModel() {

    val editPhone = MutableStateFlow("")
    val editBankIndex = MutableStateFlow(-1)
    val editBank = MutableStateFlow("")
    val editAccount = MutableStateFlow("")
    val editOwner = MutableStateFlow("")
    val editEnglish = MutableStateFlow(false)

//    private val phoneValid = MutableStateFlow(true)
//    private val accountValid = MutableStateFlow(true)
//    private val ownerValid = MutableStateFlow(true)

    val editAvailable = MutableStateFlow(true)

    private val _uiState = MutableStateFlow(MyEditUiState())
    val uiState: StateFlow<MyEditUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<MyEditUiEvents>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events: SharedFlow<MyEditUiEvents> = _events.asSharedFlow()

    init {
        observePhone()
        observeAccount()
        observeOwner()
    }

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

    fun onClickEditBtn() {
        if (uiState.value.editMode) {
            editDone()
        } else {
            setEditMode()
        }
    }

    private fun setEditMode() {
        _uiState.update { state ->
            state.copy(editMode = true)
        }
        with(uiState.value) {
            editPhone.value = phoneNumber
            editBank.value = accountType
            editAccount.value = accountNumber
            editOwner.value = accountOwner
            editEnglish.value = english
        }
    }

    fun observeEnglish(isChecked: Boolean) {
        editEnglish.value = isChecked
    }

    private fun editDone() {
        _uiState.update { state -> state.copy(editMode = false) }
        viewModelScope.launch { _events.emit(MyEditUiEvents.DoneEdit) }

    }

    private fun observePhone() {
        editPhone.onEach {
            editAvailable.emit(
                it.matches(PHONE_REGEX)
            )
        }.launchIn(viewModelScope)
    }

    private fun observeAccount() {
        editAccount.onEach {
            editAvailable.emit(
                it.matches(ACCOUNT_REGEX)
            )
        }.launchIn(viewModelScope)
    }

    private fun observeOwner() {
        editOwner.onEach {
            editAvailable.emit(
                it.length > NAME_LENGTH
            )
        }.launchIn(viewModelScope)
    }

    companion object {
        val PHONE_REGEX = Regex("""^[0-9]{11}${'$'}""")
        val ACCOUNT_REGEX = Regex("\\d+")
        const val NAME_LENGTH = 1
    }
}

data class MyEditUiState(
    val editMode: Boolean = false,
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

sealed class MyEditUiEvents {
    data object DoneEdit : MyEditUiEvents()
}