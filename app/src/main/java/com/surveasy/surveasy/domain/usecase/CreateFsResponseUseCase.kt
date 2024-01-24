package com.surveasy.surveasy.domain.usecase

import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateFsResponseUseCase @Inject constructor(
    private val repository: SurveyRepository
) {
    operator fun invoke(
        english: Boolean,
        city: String,
        district: String,
        family: String,
        houseType: String,
        job: String,
        university: String,
        major: String,
        marriage: String,
        military: String,
        pet: String
    ): Flow<BaseState<Unit>> = repository.createFsResponse(
        english,
        city,
        district,
        family,
        houseType,
        job,
        university,
        major,
        marriage,
        military,
        pet
    )
}