package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.repository.FirebaseRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend operator fun invoke(version: String): Boolean =
        repository.checkVersion(version)
}