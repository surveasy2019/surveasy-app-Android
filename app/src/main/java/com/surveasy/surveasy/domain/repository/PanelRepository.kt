package com.surveasy.surveasy.domain.repository

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.PanelDetailInfo
import com.surveasy.surveasy.domain.model.PanelInfo
import com.surveasy.surveasy.domain.model.Register
import kotlinx.coroutines.flow.Flow

interface PanelRepository {

    fun createExistPanel(
        uid: String,
        email: String,
        platform: String = "ANDROID"
    ): Flow<BaseState<Register>>

    fun createNewPanel(
        name: String,
        email: String,
        fcmToken: String,
        gender: String,
        birth: String,
        accountOwner: String,
        accountType: String,
        accountNumber: String,
        inflowPath: String,
        inflowPathEtc: String,
        phoneNumber: String,
        platform: String = "ANDROID",
        pushOn: Boolean,
        marketing: Boolean,
    ): Flow<BaseState<Register>>

    fun queryPanelInfo(): Flow<BaseState<PanelInfo>>

    fun queryPanelDetailInfo(): Flow<BaseState<PanelDetailInfo>>

    fun editPanelInfo(
        phoneNumber: String,
        accountOwner: String,
        accountType: String,
        accountNumber: String,
        english: Boolean,
    ): Flow<BaseState<Unit>>
}
