package com.surveasy.surveasy.presentation
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {
    private val _repositoriesFetchAuth = MutableLiveData<DidAuthModel>()
    val repositories1 : MutableLiveData<DidAuthModel>
        get() = _repositoriesFetchAuth


    private val db = Firebase.firestore

    init {
        Log.d(TAG, ": MainViewMode init")
    }

    suspend fun fetchDidAuth(uid : String){
        viewModelScope.launch {
            repository.fetchDidAuth(uid, _repositoriesFetchAuth)

        }
    }


}