package com.surveasy.surveasy.data.remote

import com.surveasy.surveasy.data.model.request.SignupRequest
import com.surveasy.surveasy.data.model.response.SignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SurveasyApi {
    @POST("/panel/auth/existing")
    suspend fun signupExistingPanel(@Body uid : SignupRequest): SignupResponse
}