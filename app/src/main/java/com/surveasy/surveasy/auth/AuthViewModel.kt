package com.surveasy.surveasy.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _repositoriesCheckAuth = MutableLiveData<AuthCheckModel>()
    val repositories1 : MutableLiveData<AuthCheckModel>
        get() = _repositoriesCheckAuth
    private val _repositoriesUpdateAuth = MutableLiveData<AuthModel>()
    val repositories2 : MutableLiveData<AuthModel>
        get() = _repositoriesUpdateAuth

    suspend fun checkAuthWithFB(uid: String, snsUid: String){
        viewModelScope.launch {
            repository.checkAuthWithFB(uid, snsUid, _repositoriesCheckAuth)
        }
    }

    suspend fun updateAuthStatus(uid : String, snsUid : String){
        viewModelScope.launch {
            repository.updateAuthStatus(uid, snsUid, _repositoriesUpdateAuth)
        }
    }
}