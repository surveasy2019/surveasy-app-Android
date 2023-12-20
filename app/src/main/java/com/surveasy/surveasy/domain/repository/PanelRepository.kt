package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.PanelInfo
import kotlinx.coroutines.flow.Flow

interface PanelRepository {
    fun queryPanelInfo(): Flow<BaseState<PanelInfo>>
}
