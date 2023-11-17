package com.surveasy.surveasy.presentation

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainRepository : MainRepositoryInterface {
    companion object {
        val instance = MainRepository()
    }

    private val db = Firebase.firestore

    // fetch auth info in fragment

    override suspend fun fetchDidAuth(uid: String, model: MutableLiveData<DidAuthModel>) {
        val docRef = db.collection("panelData").document(uid.toString())
        docRef.get().addOnCompleteListener { snapshot ->
            val auth: DidAuthModel
            if (snapshot.result["snsAuth"] == null) {
                auth = DidAuthModel(false)
            } else {
                auth = DidAuthModel(
                    snapshot.result["snsAuth"] as Boolean
                )
            }
            model.postValue(auth)
        }
    }
}