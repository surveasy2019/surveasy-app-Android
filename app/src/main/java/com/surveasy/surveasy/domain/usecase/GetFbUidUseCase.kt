package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetFbUidUseCase @Inject constructor(
    private val repository: FirebaseRepository
) {
    suspend operator fun invoke(email: String, pw: String): String = repository.getFbUid(email, pw)
}