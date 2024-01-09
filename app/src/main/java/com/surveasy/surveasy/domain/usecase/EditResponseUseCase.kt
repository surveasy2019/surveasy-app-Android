package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.CommonId
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditResponseUseCase @Inject constructor(private val repository: SurveyRepository) {
    operator fun invoke(sid: Int, url: String): Flow<BaseState<CommonId>> =
        repository.editResponse(sid, url)
}