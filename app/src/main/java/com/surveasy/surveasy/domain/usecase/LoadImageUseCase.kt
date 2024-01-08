package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.repository.FirebaseRepository
import javax.inject.Inject

class LoadImageUseCase @Inject constructor(private val repository: FirebaseRepository) {
    suspend operator fun invoke(uri: String, id: Int, imgName: String): String =
        repository.loadImage(uri, id, imgName)
}