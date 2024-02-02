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
        family: String,
        job: String,
        major: String?,
        pet: Int,
    ): Flow<BaseState<Unit>> = repository.createFsResponse(
        english,
        setCity(city),
        setFamily(family),
        setJob(job),
        setMajor(major),
        setPet(pet)
    )

    private fun setCity(city: String): String {
        return when (city) {
            "서울특별시" -> "SEOUL"
            "부산광역시" -> "BUSAN"
            "대구광역시" -> "DAEGU"
            "인천광역시" -> "INCHEON"
            "광주광역시" -> "GWANGJU"
            "대전광역시" -> "DAEJEON"
            "울산광역시" -> "ULSAN"
            "세종특별자치시" -> "SEJONG"
            "경기도" -> "GYEONGGI"
            "강원도" -> "GANGWON"
            "충청북도" -> "CHUNGBUK"
            "충청남도" -> "CHUNGNAM"
            "전라북도" -> "JEONBUK"
            "전라남도" -> "JEONNAM"
            "경상북도" -> "GYEONGBUK"
            "경상남도" -> "GYEONGNAM"
            "제주특별자치도" -> "JEJU"
            else -> ""
        }
    }

    private fun setJob(job: String): String {
        return when (job) {
            "중/고등학생" -> "MID_HIGH"
            "대학생" -> "UNDERGRADUATE"
            "대학원생" -> "GRADUATE"
            "사무직" -> "OFFICE"
            "경영 관리직" -> "MANAGEMENT"
            "판매/서비스직" -> "SALES"
            "자영업" -> "BUSINESS"
            "기능/생산직" -> "PRODUCTION"
            "전문직" -> "SPECIALIZED"
            "농림어업" -> "FARMING_FISHING"
            "전업주부" -> "HOMEMAKER"
            "무직" -> "NONE"
            "기타" -> "ETC"
            else -> ""
        }
    }

    private fun setMajor(major: String?): String? {
        return when (major) {
            "사회계열" -> "SOCIAL"
            "공학계열" -> "ENGINEERING"
            "인문계열" -> "HUMAN"
            "자연계열" -> "NATURE"
            "예체능계열" -> "ART_PHYSICAL"
            "의약계열" -> "MEDICAL"
            "교육계열" -> "EDUCATION"
            "기타" -> "ETC"
            else -> null
        }
    }

    private fun setFamily(family: String): String {
        return when (family) {
            "1인 가구" -> "PERSON_1"
            "2인 가구" -> "PERSON_2"
            "3인 가구" -> "PERSON_3"
            "4인 가구 이상" -> "PERSON_4"
            else -> ""
        }
    }

    private fun setPet(pet: Int): String {
        return when (pet) {
            0 -> "NONE"
            1 -> "CAT"
            2 -> "DOG"
            3 -> "ETC"
            else -> ""
        }
    }
}