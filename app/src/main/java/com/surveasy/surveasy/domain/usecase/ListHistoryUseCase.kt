package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.History
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListHistoryUseCase @Inject constructor(private val repository: SurveyRepository) {
    operator fun invoke(
        type: String,
        page: Int?,
        size: Int?,
        sort: List<String>?
    ): Flow<BaseState<History>> = repository.listHistory(type, page, size, sort)
}