package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.SurveyDetailInfo

data class SurveyDetailInfoResponse(
    val id: Int,
    val title: String,
    val reward: Int,
    val headCount: Int,
    val spendTime: String,
    val responseCount: Int,
    val targetInput: String?,
    val noticeToPanel: String?,
    val link: String,
) : BaseDataModel {
    companion object : DomainMapper<SurveyDetailInfoResponse, SurveyDetailInfo> {
        override fun SurveyDetailInfoResponse.toDomainModel(): SurveyDetailInfo = SurveyDetailInfo(
            id = id,
            title = title,
            reward = reward,
            headCount = headCount,
            spendTime = spendTime,
            responseCount = responseCount,
            targetInput = targetInput,
            noticeToPanel = noticeToPanel,
            link = link,
        )
    }
}
