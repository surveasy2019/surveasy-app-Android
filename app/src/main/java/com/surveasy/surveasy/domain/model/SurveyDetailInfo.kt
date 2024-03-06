package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class SurveyDetailInfo(
    val id: Int,
    val title: String,
    val reward: Int,
    val headCount: Int,
    val spendTime: String,
    val responseCount: Int,
    val targetInput: String?,
    val noticeToPanel: String?,
    val link: String,
    val description: String,
) : BaseDomainModel
