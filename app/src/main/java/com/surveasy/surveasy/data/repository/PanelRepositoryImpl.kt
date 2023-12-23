package com.surveasy.surveasy.data.repository

import com.surveasy.surveasy.data.model.request.ExistRegisterRequest
import com.surveasy.surveasy.data.model.request.NewRegisterRequest
import com.surveasy.surveasy.data.model.response.PanelDetailInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.PanelInfoResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.model.response.RegisterResponse.Companion.toDomainModel
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.remote.handleResponse
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.PanelDetailInfo
import com.surveasy.surveasy.domain.model.PanelInfo
import com.surveasy.surveasy.domain.model.Register
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PanelRepositoryImpl @Inject constructor(private val api: SurveasyApi) : PanelRepository {
    override fun getTempToken(): Flow<BaseState<String>> = flow {
        when (val result = handleResponse { api.getTempToken() }) {
            is BaseState.Success -> emit(BaseState.Success(result.data))
            is BaseState.Error -> emit(result)
        }
    }

    override fun createExistPanel(
        uid: String,
        email: String,
        platform: String
    ): Flow<BaseState<Register>> = flow {
        when (val result =
            handleResponse { api.createExistPanel(ExistRegisterRequest(uid, email, platform)) }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }

    override fun createNewPanel(
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
        platform: String,
        pushOn: Boolean,
        marketing: Boolean
    ): Flow<BaseState<Register>> = flow {
        when (val result =
            handleResponse {
                api.createNewPanel(
                    NewRegisterRequest(
                        name,
                        email,
                        fcmToken,
                        gender,
                        birth,
                        accountOwner,
                        accountType,
                        accountNumber,
                        inflowPath,
                        inflowPathEtc,
                        phoneNumber,
                        platform,
                        pushOn,
                        marketing
                    )
                )
            }) {
            is BaseState.Success -> emit(BaseState.Success(result.data.toDomainModel()))
            is BaseState.Error -> emit(result)
        }
    }

    override fun queryPanelInfo(): Flow<BaseState<PanelInfo>> = flow {

        when (val result = handleResponse { api.queryPanelInfo() }) {
            is BaseState.Success -> {
                emit(BaseState.Success(result.data.toDomainModel()))
            }

            is BaseState.Error -> emit(result)
        }
    }

    override fun queryPanelDetailInfo(): Flow<BaseState<PanelDetailInfo>> = flow {
        when (val result = handleResponse { api.queryPanelDetailInfo() }) {
            is BaseState.Success -> {
                emit(BaseState.Success(result.data.toDomainModel()))
            }

            is BaseState.Error -> emit(result)
        }
    }

}