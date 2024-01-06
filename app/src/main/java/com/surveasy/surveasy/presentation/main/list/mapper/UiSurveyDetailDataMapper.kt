package com.surveasy.surveasy.presentation.main.list.mapper

import com.surveasy.surveasy.domain.model.SurveyDetailInfo
import com.surveasy.surveasy.presentation.main.list.model.UiSurveyDetailData

fun SurveyDetailInfo.toSurveyDetailData(): UiSurveyDetailData = UiSurveyDetailData(
    title = title,
    reward = reward,
    spendTime = spendTime,
    responseCount = responseCount,
    targetInput = targetInput ?: "모두",
    noticeToPanel = noticeToPanel ?: "성실한 참여 부탁드립니다.",
    surveyDescription = ""
)