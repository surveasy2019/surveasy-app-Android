package com.surveasy.surveasy.presentation.main.my.history.mapper

import com.surveasy.surveasy.domain.model.HistorySurvey
import com.surveasy.surveasy.presentation.main.my.history.model.UiHistorySurveyData

fun HistorySurvey.toUiHistorySurveyData(): UiHistorySurveyData = UiHistorySurveyData(
    id = id,
    title = title,
    reward = reward,
    imgUrl = imgUrl,
    createdAt = createdAt,
    sentAt = sentAt
)