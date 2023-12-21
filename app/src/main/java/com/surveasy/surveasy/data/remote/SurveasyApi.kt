package com.surveasy.surveasy.data.remote

import com.surveasy.surveasy.data.model.response.PanelInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveasyApi {

    @GET("panel/auth/token/1")
    suspend fun getTempToken(): Response<String>

    //home
    @GET("panel/home")
    suspend fun queryPanelInfo(): Response<PanelInfoResponse>
}