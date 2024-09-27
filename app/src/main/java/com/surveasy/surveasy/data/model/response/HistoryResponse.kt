package com.surveasy.surveasy.data.model.response

import com.google.gson.annotations.SerializedName
import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.data.model.response.HistorySurveyResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.PageInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.domain.model.History
import com.surveasy.surveasy.domain.model.HistorySurvey
import com.surveasy.surveasy.domain.model.PageInfo

data class HistoryResponse(
    val type: String,
    val pageInfo: PageInfoResponse,
    val responseList: List<HistorySurveyResponse>,
) : BaseDataModel {
    companion object : DomainMapper<HistoryResponse, History> {
        override fun HistoryResponse.toDomainModel(): History = History(
            type = type,
            pageInfo = pageInfo.toDomainModel(),
            responseList = responseList.map { it.toDomainModel() }
        )
    }
}

data class HistorySurveyResponse(
    val id: Int,
    @SerializedName("surveyTitle") val title: String,
    @SerializedName("surveyReward") val reward: Int,
    val imgUrl: String,
    val createdAt: String,
    val sentAt: String?,
    val responseStatus: String?,
    val surveyStatus: String?,
) : BaseDataModel {
    companion object : DomainMapper<HistorySurveyResponse, HistorySurvey> {
        override fun HistorySurveyResponse.toDomainModel(): HistorySurvey = HistorySurvey(
            id = id,
            title = title,
            reward = reward,
            imgUrl = imgUrl,
            createdAt = createdAt,
            sentAt = sentAt,
            responseStatus = responseStatus,
            surveyStatus = surveyStatus,
        )
    }
}