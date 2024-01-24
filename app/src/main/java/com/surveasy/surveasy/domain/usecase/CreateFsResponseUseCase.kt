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
        marriage: Int,
        military: Int,
        pet: Int,
    ): Flow<BaseState<Unit>> = repository.createFsResponse(
        english,
        city,
        district,
        setFamily(family),
        setHouseType(houseType),
        job,
        university,
        major,
        setMarriage(marriage),
        setMilitary(military),
        setPet(pet)
    )

    private fun setFamily(family: String): String {
        return when (family) {
            "1인 가구" -> "PERSON_1"
            "2인 가구" -> "PERSON_2"
            "3인 가구" -> "PERSON_3"
            "4인 가구 이상" -> "PERSON_4"
            else -> ""
        }
    }

    private fun setHouseType(house: String): String {
        return when (house) {
            "아파트/주상복합" -> "APT"
            "단독주택" -> "DETACHED"
            "오피스텔/원룸" -> "OFFICETEL"
            "다세대/빌라/연립" -> "MULTIPLEX"
            "기숙사" -> "DORMITORY"
            "기타" -> "ETC"
            else -> ""
        }
    }

    private fun setMarriage(marry: Int): String {
        return when (marry) {
            0 -> "SINGLE"
            1 -> "MARRIED"
            2 -> "DIVORCE"
            else -> ""
        }
    }

    private fun setMilitary(military: Int): String {
        return when (military) {
            0 -> "DONE"
            1 -> "YET"
            2 -> "NONE"
            else -> ""
        }
    }

    private fun setPet(pet: Int): String {
        return when (pet) {
            0 -> "DONE"
            1 -> "CAT"
            2 -> "DOG"
            3 -> "ETC"
            else -> ""
        }
    }
}