package com.surveasy.surveasy.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.base.StatusCode
import com.surveasy.surveasy.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseRepository {
    override fun getUid(email: String, pw: String): Flow<BaseState<String>> = flow {
        var user: String? = null
        firebaseAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("TEST", "${firebaseAuth.currentUser?.uid}")
                user = firebaseAuth.uid

            }

        }

        emit(
            if (user != null) BaseState.Success(user!!) else BaseState.Error(
                StatusCode.EMPTY,
                "login failed"
            )
        )

    }

}