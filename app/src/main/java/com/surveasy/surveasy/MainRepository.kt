package com.surveasy.surveasy

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.amplitude.api.Amplitude
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.surveasy.surveasy.home.Opinion.AnswerItem
import com.surveasy.surveasy.home.Opinion.OpinionItem
import com.surveasy.surveasy.home.contribution.ContributionItems
import com.surveasy.surveasy.list.UserSurveyItem
import com.surveasy.surveasy.login.CurrentUser

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
            if (snapshot.result["SNSAuth"] == null) {
                auth = DidAuthModel(false)
            } else {
                auth = DidAuthModel(
                    snapshot.result["SNSAuth"] as Boolean
                )
            }
            model.postValue(auth)
        }
    }
}