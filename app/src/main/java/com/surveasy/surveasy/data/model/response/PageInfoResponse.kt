package com.surveasy.surveasy.data.model.response

import com.surveasy.surveasy.data.mapper.DomainMapper
import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.model.PageInfo

data class PageInfoResponse(
    val pageNum: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
) : BaseDataModel {
    companion object : DomainMapper<PageInfoResponse, PageInfo> {
        override fun PageInfoResponse.toDomainModel(): PageInfo = PageInfo(
            pageNum = pageNum,
            pageSize = pageSize,
            totalElements = totalElements,
            totalPages = totalPages
        )
    }
}