package com.surveasy.surveasy.data.repository

import com.surveasy.surveasy.data.model.request.ResponseImgRequest
import com.surveasy.surveasy.data.model.response.CommonIdResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.HistoryResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.HomeSurveyResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.SurveyDetailInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.SurveyResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.remote.handleResponse
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.CommonId
import com.surveasy.surveasy.domain.model.History
import com.surveasy.surveasy.domain.model.HomeSurvey
import com.surveasy.surveasy.domain.model.Survey
import com.surveasy.surveasy.domain.model.SurveyDetailInfo
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val api: SurveasyApi
) : SurveyRepository {
    override fun listSurvey(
        page: Int?,
        size: Int?,
        sort: List<String>?
    ): Flow<BaseState<Survey>> = flow {
        when (val result = handleResponse { api.listSurvey(page, size, sort) }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }

    override fun listHomeSurvey(): Flow<BaseState<HomeSurvey>> = flow {
        when (val result = handleResponse { api.listHomeSurvey() }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }

    override fun querySurveyDetail(sid: Int): Flow<BaseState<SurveyDetailInfo>> = flow {
        when (val result = handleResponse { api.querySurveyDetail(sid) }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }

    override fun listHistory(type: String): Flow<BaseState<History>> = flow {
        when (val result = handleResponse { api.listHistory(type) }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }

    override fun createResponse(sid: Int, url: String): Flow<BaseState<Unit>> = flow {
        when (val result = handleResponse { api.createResponse(sid, ResponseImgRequest(url)) }) {
            is BaseState.Success -> emit(BaseState.Success(Unit))
            is BaseState.Error -> emit(result)
        }
    }

    override fun editResponse(sid: Int, url: String): Flow<BaseState<CommonId>> = flow {
        when (val result = handleResponse { api.editResponse(sid, ResponseImgRequest(url)) }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }
}