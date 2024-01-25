package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.AccountInfo
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryAccountInfoUseCase @Inject constructor(
    private val repository: SurveyRepository
) {
    operator fun invoke(): Flow<BaseState<AccountInfo>> = repository.queryAccountInfo()
}