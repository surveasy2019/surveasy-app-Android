package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTempTokenUseCase @Inject constructor(
    private val repository: PanelRepository
) {
    operator fun invoke(): Flow<BaseState<String>> = repository.getTempToken()
}