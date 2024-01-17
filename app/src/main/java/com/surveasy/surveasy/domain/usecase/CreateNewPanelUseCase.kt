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
        platform: String,
        accountOwner: String,
        accountType: String,
        accountNumber: String,
        inflowPath: String,
        inflowPathEtc: String?,
        pushOn: Boolean,
        marketingAgree: Boolean,
    ): Flow<BaseState<Register>> = repository.createNewPanel(
        platform,
        accountOwner,
        setBank(accountType),
        accountNumber,
        setInflow(inflowPath),
        inflowPathEtc,
        pushOn,
        marketingAgree
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

    private fun setInflow(inflow: String): String {
        return when (inflow) {
            "카카오톡" -> "KAKAO"
            "에브리타임" -> "EVERYTIME"
            "인스타그램" -> "INSTAGRAM"
            "지인추천" -> "ACQUAINTANCE"
            "기타" -> "ETC"
            else -> ""
        }
    }
}