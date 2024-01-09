package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class HomeSurvey(
    val surveyAppHomeList: List<HomeSurveyInfo>
) : BaseDomainModel

data class HomeSurveyInfo(
    val id: Int,
    val title: String,
    val reward: Int,
    val targetInput: String?,
    val responseCount: Int,
    val noticeToPanel: String?,
) : BaseDomainModel
