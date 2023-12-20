package com.surveasy.surveasy.data.repository

import com.surveasy.surveasy.data.model.response.PanelInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.remote.runRemote
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.PanelInfo
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PanelRepositoryImpl @Inject constructor(private val api : SurveasyApi): PanelRepository {
    override fun queryPanelInfo(): Flow<BaseState<PanelInfo>> = flow {

        when(val result = runRemote { api.queryPanelInfo() }){
            is BaseState.Success -> {
                emit(BaseState.Success(result.data.toDomainModel()))
            }
            is BaseState.Error -> emit(result)
        }
    }

}