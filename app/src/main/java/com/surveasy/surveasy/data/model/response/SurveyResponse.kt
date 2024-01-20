package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.data.model.response.SurveyInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.domain.model.Survey
import com.surveasy.surveasy.domain.model.SurveyInfo

data class SurveyResponse(
    val surveyAppList: List<SurveyInfoResponse>,
    val didFirstSurvey: Boolean,
) : BaseDataModel {
    companion object : DomainMapper<SurveyResponse, Survey> {
        override fun SurveyResponse.toDomainModel(): Survey = Survey(
            surveyAppList = surveyAppList.map { it.toDomainModel() },
            didFirstSurvey = didFirstSurvey
        )
    }
}

data class SurveyInfoResponse(
    val id: Int,
    val title: String,
    val reward: Int,
    val headCount: Int,
    val spendTime: String,
    val targetInput: String?,
    val status: String,
    val responseCount: Int,
    val participated: Boolean,
) : BaseDataModel {
    companion object : DomainMapper<SurveyInfoResponse, SurveyInfo> {
        override fun SurveyInfoResponse.toDomainModel(): SurveyInfo = SurveyInfo(
            id = id,
            title = title,
            reward = reward,
            headCount = headCount,
            spendTime = spendTime,
            targetInput = targetInput,
            status = status,
            responseCount = responseCount,
            participated = participated
        )

    }
}
