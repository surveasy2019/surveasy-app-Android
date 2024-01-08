package com.surveasy.surveasy.data.remote

import com.surveasy.surveasy.data.model.request.EditInfoRequest
import com.surveasy.surveasy.data.model.request.ExistRegisterRequest
import com.surveasy.surveasy.data.model.request.NewRegisterRequest
import com.surveasy.surveasy.data.model.response.HistoryResponse
import com.surveasy.surveasy.data.model.response.HomeSurveyResponse
import com.surveasy.surveasy.data.model.response.PanelDetailInfoResponse
import com.surveasy.surveasy.data.model.response.PanelInfoResponse
import com.surveasy.surveasy.data.model.response.RegisterResponse
import com.surveasy.surveasy.data.model.response.SurveyDetailInfoResponse
import com.surveasy.surveasy.data.model.response.SurveyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface SurveasyApi {

    @GET("panel/auth/token/1")
    suspend fun getTempToken(): Response<String>

    @POST("panel/signup/existing")
    suspend fun createExistPanel(
        @Body body: ExistRegisterRequest
    ): Response<RegisterResponse>

    @POST("panel/signup")
    suspend fun createNewPanel(
        @Body body: NewRegisterRequest
    ): Response<RegisterResponse>

    @GET("panel")
    suspend fun queryPanelDetailInfo(): Response<PanelDetailInfoResponse>

    @PATCH("panel")
    suspend fun editPanelInfo(
        @Body body: EditInfoRequest
    ): Response<Unit>

    //home
    @GET("panel/home")
    suspend fun queryPanelInfo(): Response<PanelInfoResponse>

    //survey
    @GET("survey/app")
    suspend fun listSurvey(): Response<SurveyResponse>

    @GET("survey/app/home")
    suspend fun listHomeSurvey(): Response<HomeSurveyResponse>

    @GET("survey/app/{surveyId}")
    suspend fun querySurveyDetail(@Path("surveyId") sid: Int): Response<SurveyDetailInfoResponse>

    //history
    @GET("response/{type}")
    suspend fun listHistory(@Path("type") type: String): Response<HistoryResponse>
}