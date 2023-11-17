package com.surveasy.surveasy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.surveasy.surveasy.data.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val signupRepository: SignupRepository): ViewModel() {
    fun test(uid : String){
        signupRepository.signupExistingPanel(uid).onEach {
            println(it)
        }.launchIn(viewModelScope)
    }
}