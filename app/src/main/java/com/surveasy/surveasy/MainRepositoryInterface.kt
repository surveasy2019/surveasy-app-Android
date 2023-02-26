package com.surveasy.surveasy


import androidx.lifecycle.MutableLiveData
import com.surveasy.surveasy.login.CurrentUser

interface MainRepositoryInterface {

    //fetchCurrentUser
    suspend fun fetchDidAuth(uid : String, model : MutableLiveData<DidAuthModel>)


}