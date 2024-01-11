package com.surveasy.surveasy.presentation.main.home.mapper

import com.surveasy.surveasy.domain.model.HomeSurveyInfo
import com.surveasy.surveasy.presentation.main.home.model.UiHomeListData

fun HomeSurveyInfo.toUiHomeListData(): UiHomeListData = UiHomeListData(
    id = id,
    title = title,
    reward = reward,
    targetInput = targetInput,
    responseCount = responseCount,
)