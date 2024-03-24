package com.surveasy.surveasy.presentation.main.survey.mapper

import com.surveasy.surveasy.domain.model.SurveyDetailInfo
import com.surveasy.surveasy.presentation.main.survey.model.UiSurveyDetailData

fun SurveyDetailInfo.toSurveyDetailData(): UiSurveyDetailData = UiSurveyDetailData(
    title = title,
    reward = reward,
    headCount = headCount,
    spendTime = spendTime,
    responseCount = if(responseCount > headCount) headCount - 1 else responseCount,
    targetInput = targetInput ?: "모두",
    noticeToPanel = noticeToPanel ?: "성실한 참여 부탁드립니다.",
    surveyDescription = description,
    link = link,
)