package com.surveasy.surveasy.data.repository

import com.surveasy.surveasy.data.model.response.SurveyResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.remote.handleResponse
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.Survey
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val api: SurveasyApi
) : SurveyRepository {
    override fun listSurvey(): Flow<BaseState<Survey>> = flow {
        when (val result = handleResponse { api.listSurvey() }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }
}