package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.PanelInfo

data class PanelInfoResponse(
    val name : String,
    val count: Int,
    val rewardCurrent : Int,
    val rewardTotal: Int,
): BaseDataModel {
    companion object : DomainMapper<PanelInfoResponse, PanelInfo>{
        override fun PanelInfoResponse.toDomainModel(): PanelInfo = PanelInfo(
            name = name,
            count = count,
            rewardCurrent = rewardCurrent,
            rewardTotal = rewardTotal
        )
    }
}
