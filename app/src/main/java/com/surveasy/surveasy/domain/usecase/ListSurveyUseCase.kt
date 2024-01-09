package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.Survey
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListSurveyUseCase @Inject constructor(val repository: SurveyRepository) {
    operator fun invoke(): Flow<BaseState<Survey>> = repository.listSurvey()
}