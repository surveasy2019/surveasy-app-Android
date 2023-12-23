package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.Register
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateExistPanelUseCase @Inject constructor(
    private val repository: PanelRepository
) {
    operator fun invoke(uid: String, email: String) : Flow<BaseState<Register>> = repository.createExistPanel(uid, email)
}