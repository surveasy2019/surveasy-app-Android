package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.PanelInfo
import com.surveasy.surveasy.domain.model.Register
import kotlinx.coroutines.flow.Flow

interface PanelRepository {

    fun getTempToken(): Flow<BaseState<String>>

    fun createExistPanel(
        uid: String,
        email: String,
        platform: String = "ANDROID"
    ): Flow<BaseState<Register>>

    fun queryPanelInfo(): Flow<BaseState<PanelInfo>>
}
