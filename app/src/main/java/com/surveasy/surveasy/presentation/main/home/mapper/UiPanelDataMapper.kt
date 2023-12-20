package com.surveasy.surveasy.presentation.main.home.mapper

import com.surveasy.surveasy.domain.model.PanelInfo
import com.surveasy.surveasy.presentation.main.home.model.UiPanelData

fun PanelInfo.toUiPanelData(): UiPanelData{
    return UiPanelData(
        name = name,
        count = count,
        rewardCurrent = rewardCurrent,
        rewardTotal = rewardTotal
    )
}