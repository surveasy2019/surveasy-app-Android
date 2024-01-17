package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.model.KakaoInfo
import com.surveasy.surveasy.domain.repository.PanelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class KakaoSignupUseCase @Inject constructor(
    private val repository: PanelRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        phoneNumber: String,
        gender: String,
        birth: String,
        authProvider: String
    ): Flow<BaseState<KakaoInfo>> =
        repository.kakaoSignup(name, email, phoneNumber, gender, birth, authProvider)
}