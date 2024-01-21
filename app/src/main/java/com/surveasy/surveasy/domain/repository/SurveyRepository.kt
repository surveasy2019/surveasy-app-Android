package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.CommonId
import com.surveasy.surveasy.domain.model.History
import com.surveasy.surveasy.domain.model.HomeSurvey
import com.surveasy.surveasy.domain.model.Survey
import com.surveasy.surveasy.domain.model.SurveyDetailInfo
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun listSurvey(
        page: Int?,
        size: Int?,
        sort: List<String>?
    ): Flow<BaseState<Survey>>

    fun listHomeSurvey(): Flow<BaseState<HomeSurvey>>

    fun querySurveyDetail(sid: Int): Flow<BaseState<SurveyDetailInfo>>

    fun listHistory(
        type: String,
        page: Int?,
        size: Int?,
        sort: List<String>?
    ): Flow<BaseState<History>>

    fun createResponse(sid: Int, url: String): Flow<BaseState<Unit>>

    fun editResponse(sid: Int, url: String): Flow<BaseState<CommonId>>
}