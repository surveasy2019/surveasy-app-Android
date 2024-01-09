package com.surveasy.surveasy.presentation.main.list.model

data class UiSurveyListData(
    val id: Int,
    val title: String,
    val reward: Int,
    val spendTime: String,
    val targetInput: String?,
    val status: String,
    val responseCount: Int,
    val participated: Boolean,
)
