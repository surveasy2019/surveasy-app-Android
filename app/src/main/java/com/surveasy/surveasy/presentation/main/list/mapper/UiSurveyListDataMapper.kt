package com.surveasy.surveasy.presentation.main.list.mapper

import com.surveasy.surveasy.domain.model.SurveyInfo
import com.surveasy.surveasy.presentation.main.list.model.UiSurveyListData

fun SurveyInfo.toUiSurveyListData(): UiSurveyListData = UiSurveyListData(
    id = id,
    title = title,
    reward = reward,
    spendTime = spendTime,
    targetInput = targetInput,
    status = status,
    responseCount = responseCount,
    participated = participated
)