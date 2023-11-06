package com.surveasy.surveasy.data.repository

import com.surveasy.surveasy.data.model.response.SignupResponse
import kotlinx.coroutines.flow.Flow

interface SignupRepository {
    fun signupExistingPanel(uid : String): Flow<SignupResponse>
}