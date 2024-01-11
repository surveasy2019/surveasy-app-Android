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
        repository.editPanelInfo(
            phoneNumber,
            accountOwner,
            setBank(accountType),
            accountNumber,
            english
        )

    private fun setBank(bank: String): String {
        return when (bank) {
            "국민" -> "KB"
            "하나" -> "HANA"
            "우리" -> "WOORI"
            "신한" -> "SHINHAN"
            "농협" -> "NH"
            "수협" -> "SH"
            "IBK 기업" -> "IBK"
            "새마을금고" -> "MG"
            "카카오뱅크" -> "KAKAO"
            "토스뱅크" -> "TOSS"
            else -> ""
        }
    }
}