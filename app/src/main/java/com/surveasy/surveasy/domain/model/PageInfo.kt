package com.surveasy.surveasy.domain.model

import com.surveasy.surveasy.domain.base.BaseDomainModel

data class PageInfo(
    val pageNum: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
) : BaseDomainModel