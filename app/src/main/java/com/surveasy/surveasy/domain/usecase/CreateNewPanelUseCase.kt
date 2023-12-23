package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.Register
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateNewPanelUseCase @Inject constructor(
    private val repository: PanelRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        fcmToken: String,
        gender: String,
        birth: String,
        accountOwner: String,
        accountType: String,
        accountNumber: String,
        inflowPath: String,
        inflowPathEtc: String,
        phoneNumber: String,
        platform: String,
        pushOn: Boolean,
        marketing: Boolean
    ): Flow<BaseState<Register>> = repository.createNewPanel(
        name,
        email,
        fcmToken,
        gender,
        birth,
        accountOwner,
        accountType,
        accountNumber,
        inflowPath,
        inflowPathEtc,
        phoneNumber,
        platform,
        pushOn,
        marketing
    )
}