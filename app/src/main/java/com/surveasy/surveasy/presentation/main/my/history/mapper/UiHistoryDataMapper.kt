package com.surveasy.surveasy.presentation.main.my.history.mapper

import com.surveasy.surveasy.domain.model.History
import com.surveasy.surveasy.domain.model.HistorySurvey
import com.surveasy.surveasy.presentation.main.my.history.model.UiHistoryData
import com.surveasy.surveasy.presentation.main.my.history.model.UiHistorySurveyData

fun History.toUiHistoryData(): UiHistoryData = UiHistoryData(
    reward = 0,
    account = "",
    owner = "",
    pageNum = pageInfo.pageNum,
    pageSize = pageInfo.pageSize,
    totalElements = pageInfo.totalElements,
    totalPages = pageInfo.totalPages,
    isBefore = type == "Before",
    list = responseList.map { it.toUiHistorySurveyData() }
)

fun HistorySurvey.toUiHistorySurveyData(): UiHistorySurveyData = UiHistorySurveyData(
    id = id,
    title = title,
    reward = reward,
    imgUrl = imgUrl,
    createdAt = createdAt,
    sentAt = sentAt
)