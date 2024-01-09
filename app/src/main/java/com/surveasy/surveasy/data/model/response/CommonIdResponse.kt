package com.surveasy.surveasy.data.model.response

import com.google.gson.annotations.SerializedName
import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.CommonId

data class CommonIdResponse(
    @SerializedName("responseId") val id: Int,
) : BaseDataModel {
    companion object : DomainMapper<CommonIdResponse, CommonId> {
        override fun CommonIdResponse.toDomainModel(): CommonId = CommonId(
            id = id
        )
    }
}
