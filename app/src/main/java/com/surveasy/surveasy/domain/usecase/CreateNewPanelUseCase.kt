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
        gender: Boolean,
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
        setGender(gender),
        birth,
        accountOwner,
        setBank(accountType),
        accountNumber,
        inflowPath,
        inflowPathEtc,
        phoneNumber,
        platform,
        pushOn,
        marketing
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

    private fun setGender(isMale: Boolean): String {
        return if (isMale) "MALE" else "FEMALE"
    }
}