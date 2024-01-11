package com.surveasy.surveasy.presentation.main.my.mapper

import com.surveasy.surveasy.domain.model.PanelDetailInfo
import com.surveasy.surveasy.presentation.main.my.model.UiPanelDetailData

fun PanelDetailInfo.toUiPanelDetailData(): UiPanelDetailData = UiPanelDetailData(
    name = name,
    birth = birth,
    gender = gender,
    email = email,
    phoneNumber = phoneNumber,
    accountOwner = accountOwner,
    accountType = accountType,
//    when (accountType) {
//        "KB" -> "국민"
//        "HANA" -> "하나"
//        "WOORI" -> "우리"
//        "SHINHAN" -> "신한"
//        "NH" -> "농협"
//        "SH" -> "수협"
//        "IBK" -> "IBK 기업"
//        "MG" -> "새마을금고"
//        "KAKAO" -> "카카오뱅크"
//        "TOSS" -> "토스뱅크"
//        else -> ""
//    },
    accountNumber = accountNumber,
    english = english

)