package com.surveasy.surveasy.data.mapper

import com.surveasy.surveasy.data.model.BaseDataModel
import com.surveasy.surveasy.domain.base.BaseDomainModel

interface DomainMapper<in R : BaseDataModel, out D : BaseDomainModel> {
    fun R.toDomainModel() : D
}