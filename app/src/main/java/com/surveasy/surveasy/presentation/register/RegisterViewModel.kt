package com.surveasy.surveasy.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

sealed class RegisterEvents{
    object NavigateToRegisterWarn : RegisterEvents()
}

class RegisterViewModel : ViewModel() {
    val agreeAll = MutableStateFlow(false)
    val agreeMust1 = MutableStateFlow(false)
    val agreeMust2 = MutableStateFlow(false)
    val agreeMarketing = MutableStateFlow(false)

    private val _events = MutableSharedFlow<RegisterEvents>(
        replay = 0
    )

    val events : SharedFlow<RegisterEvents> = _events.asSharedFlow()

    init {
        checkAgreeAll()
        checkMust1()
        checkMust2()
        checkMarketing()
    }

    fun navigateToWarn(){
        viewModelScope.launch {
            _events.emit(RegisterEvents.NavigateToRegisterWarn)
        }
    }

    // all click logic 추가
    private fun checkAgreeAll() {
        agreeAll.onEach { all ->
            if(all){
                agreeMust1.emit(true)
                agreeMust2.emit(true)
                agreeMarketing.emit(true)
            }

        }.launchIn(viewModelScope)
    }

    private fun checkMust1(){
        agreeMust1.onEach { check ->
            agreeMust1.emit(check)
            agreeAll.emit(
                isAllChecked()
            )
        }.launchIn(viewModelScope)
    }

    private fun checkMust2(){
        agreeMust2.onEach { check ->
            agreeMust2.emit(check)
            agreeAll.emit(
                isAllChecked()
            )
        }.launchIn(viewModelScope)
    }

    private fun checkMarketing(){
        agreeMarketing.onEach { check ->
            agreeMarketing.emit(check)
            agreeAll.emit(
                isAllChecked()
            )
        }.launchIn(viewModelScope)
    }

    private fun isAllChecked() = agreeMust1.value && agreeMust2.value && agreeMarketing.value
}