package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.Survey
import com.surveasy.surveasy.domain.model.SurveyDetailInfo
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun listSurvey(): Flow<BaseState<Survey>>

    fun querySurveyDetail(sid: Int): Flow<BaseState<SurveyDetailInfo>>
}