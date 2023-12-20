package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class PanelInfo(
    val name : String,
    val count: Int,
    val rewardCurrent : Int,
    val rewardTotal: Int,
): BaseDomainModel
