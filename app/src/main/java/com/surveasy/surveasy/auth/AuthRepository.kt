package com.surveasy.surveasy.auth

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AuthRepository : AuthRepositoryInterface {
    private val db = Firebase.firestore
    override suspend fun checkAuthWithFB(
        uid: String,
        snsUid: String,
        model: MutableLiveData<AuthCheckModel>
    ) {
        var flag = false
        db.collection("snsAuthCheck").whereEqualTo("fbUid", uid)
            .get()
            .addOnSuccessListener { snapshot ->
                for (res in snapshot) {
                    if (res.id == snsUid) flag = true
                }
                model.postValue(AuthCheckModel(flag))

            }
    }

    override suspend fun updateAuthStatus(uid: String, snsUid: String, model: MutableLiveData<AuthModel>) {
        db.collection("panelData").document(uid.toString())
            .update("snsAuth", true, "snsUid", snsUid)
        val map = hashMapOf("fbUid" to uid, "snsUid" to snsUid)
        db.collection("snsAuthCheck").document(snsUid).set(map)
            .addOnSuccessListener { model.postValue(AuthModel(true)) }

    }
}