package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class History(
    val type: String,
    val pageInfo: PageInfo,
    val responseList: List<HistorySurvey>,
) : BaseDomainModel

data class HistorySurvey(
    val id: Int,
    val title: String,
    val reward: Int,
    val imgUrl: String,
    val createdAt: String,
    val sentAt: String?,
    val responseStatus: String?,
    val surveyStatus: String?,
) : BaseDomainModel
