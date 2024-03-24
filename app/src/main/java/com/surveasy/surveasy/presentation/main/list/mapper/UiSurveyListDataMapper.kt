package com.surveasy.surveasy.presentation.main.list.mapper

import com.surveasy.surveasy.domain.model.SurveyInfo
import com.surveasy.surveasy.presentation.main.list.model.UiSurveyListData

fun SurveyInfo.toUiSurveyListData(): UiSurveyListData = UiSurveyListData(
    id = id,
    title = title,
    reward = reward,
    headCount = headCount,
    spendTime = spendTime,
    targetInput = targetInput ?: "모두",
    status = status,
    responseCount = if(responseCount > headCount) headCount - 1 else responseCount,
    participated = participated,
    overdue = overdue
)