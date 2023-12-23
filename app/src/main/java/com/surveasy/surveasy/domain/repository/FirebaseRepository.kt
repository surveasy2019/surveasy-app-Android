package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    fun getUid(email : String, pw : String): Flow<BaseState<String>>
}