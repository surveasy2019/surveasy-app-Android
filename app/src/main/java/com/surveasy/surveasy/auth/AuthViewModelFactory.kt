package com.surveasy.surveasy.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.surveasy.surveasy.MainRepository

class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AuthRepository::class.java).newInstance(repository)

    }
}