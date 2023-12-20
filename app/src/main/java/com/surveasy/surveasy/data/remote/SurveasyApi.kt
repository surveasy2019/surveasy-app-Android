package com.surveasy.surveasy.data.remote

import com.surveasy.surveasy.data.model.response.PanelInfoResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveasyApi {

    //home
    @GET("panel/home")
    suspend fun queryPanelInfo(): Response<PanelInfoResponse>
}