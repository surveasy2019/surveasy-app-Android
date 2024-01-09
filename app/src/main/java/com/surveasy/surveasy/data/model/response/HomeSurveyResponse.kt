package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.data.model.response.HomeSurveyInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.domain.model.HomeSurvey
import com.surveasy.surveasy.domain.model.HomeSurveyInfo


data class HomeSurveyResponse(
    val surveyAppHomeList: List<HomeSurveyInfoResponse>
) : BaseDataModel {
    companion object : DomainMapper<HomeSurveyResponse, HomeSurvey> {
        override fun HomeSurveyResponse.toDomainModel(): HomeSurvey = HomeSurvey(
            surveyAppHomeList = surveyAppHomeList.map { it.toDomainModel() }
        )
    }
}

data class HomeSurveyInfoResponse(
    val id: Int,
    val title: String,
    val reward: Int,
    val targetInput: String?,
    val responseCount: Int,
    val noticeToPanel: String?,
) : BaseDataModel {
    companion object : DomainMapper<HomeSurveyInfoResponse, HomeSurveyInfo> {
        override fun HomeSurveyInfoResponse.toDomainModel(): HomeSurveyInfo = HomeSurveyInfo(
            id = id,
            title = title,
            reward = reward,
            targetInput = targetInput,
            responseCount = responseCount,
            noticeToPanel = noticeToPanel
        )

    }
}