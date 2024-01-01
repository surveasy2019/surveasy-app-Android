package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditPanelInfoUseCase @Inject constructor(
    private val repository: PanelRepository
) {
    operator fun invoke(
        phoneNumber: String,
        accountOwner: String,
        accountType: String,
        accountNumber: String,
        english: Boolean,
    ): Flow<BaseState<Unit>> =
        repository.editPanelInfo(phoneNumber, accountOwner, accountType, accountNumber, english)
}