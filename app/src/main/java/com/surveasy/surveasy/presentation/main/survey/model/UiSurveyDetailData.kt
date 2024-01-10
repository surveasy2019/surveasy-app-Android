package com.surveasy.surveasy.presentation.main.survey.model

data class UiSurveyDetailData(
    val title: String,
    val reward: Int,
    val headCount: Int,
    val spendTime: String,
    val responseCount: Int,
    val targetInput: String,
    val noticeToPanel: String,
    val surveyDescription: String,
    val link: String,
)
