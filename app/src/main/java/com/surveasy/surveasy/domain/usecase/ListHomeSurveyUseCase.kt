package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.HomeSurvey
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListHomeSurveyUseCase @Inject constructor(private val repository: SurveyRepository) {
    operator fun invoke(): Flow<BaseState<HomeSurvey>> = repository.listHomeSurvey()
}