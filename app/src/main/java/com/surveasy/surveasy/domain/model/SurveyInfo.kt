package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class Survey(
    val surveyAppList: List<SurveyInfo>,
    val didFirstSurvey: Boolean,
) : BaseDomainModel

data class SurveyInfo(
    val id: Int,
    val title: String,
    val reward: Int,
    val headCount: Int,
    val spendTime: String,
    val targetInput: String?,
    val status: String,
    val responseCount: Int,
    val participated: Boolean,
) : BaseDomainModel
