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
    accountNumber = accountNumber,
    english = english

)