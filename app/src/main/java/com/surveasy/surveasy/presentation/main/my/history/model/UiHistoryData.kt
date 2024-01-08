package com.surveasy.surveasy.presentation.main.my.history.model

data class UiHistoryData(
    val reward: Int,
    val account: String,
    val owner: String,
    val pageNum: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val isBefore: Boolean,
    val list: List<UiHistorySurveyData>,
)

data class UiHistorySurveyData(
    val id: Int,
    val title: String,
    val reward: Int,
    val imgUrl: String,
    val createdAt: String,
    val sentAt: String?,
)
