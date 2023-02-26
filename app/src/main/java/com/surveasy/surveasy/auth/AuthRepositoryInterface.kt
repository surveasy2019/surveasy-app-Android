package com.surveasy.surveasy.auth

import androidx.lifecycle.MutableLiveData

interface AuthRepositoryInterface {
    //중복 체크
    suspend fun checkAuthWithFB(uid: String, snsUid: String, model: MutableLiveData<AuthCheckModel>)

    // update panel auth info & set sns uid docs
    suspend fun updateAuthStatus(uid : String, snsUid: String, model : MutableLiveData<AuthModel>)

}