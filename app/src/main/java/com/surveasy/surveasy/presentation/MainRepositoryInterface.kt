package com.surveasy.surveasy.presentation


import androidx.lifecycle.MutableLiveData

interface MainRepositoryInterface {

    //fetchCurrentUser
    suspend fun fetchDidAuth(uid : String, model : MutableLiveData<DidAuthModel>)


}