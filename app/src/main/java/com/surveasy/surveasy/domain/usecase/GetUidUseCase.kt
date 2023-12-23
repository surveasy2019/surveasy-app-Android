package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUidUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(email: String, pw: String): Flow<BaseState<String>> = repository.getUid(email, pw)
}