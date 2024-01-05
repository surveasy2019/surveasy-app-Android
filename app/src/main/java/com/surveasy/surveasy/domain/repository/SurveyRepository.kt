package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.Survey
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun listSurvey(): Flow<BaseState<Survey>>
}