package com.surveasy.surveasy.data.repository_impl

import com.surveasy.surveasy.data.model.request.SignupRequest
import com.surveasy.surveasy.data.model.response.SignupResponse
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.repository.SignupRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(private val surveasyApi: SurveasyApi): SignupRepository {
    override fun signupExistingPanel(uid: String): Flow<SignupResponse> {
        return flow { emit(surveasyApi.signupExistingPanel(SignupRequest(uid))) }
    }

}