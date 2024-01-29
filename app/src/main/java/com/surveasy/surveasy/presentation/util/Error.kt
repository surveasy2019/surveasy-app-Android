package com.surveasy.surveasy.presentation.util

object ErrorMsg {
    const val SIGNUP_ERROR = "회원가입에 실패했습니다. 다시 시도해주세요."
    const val GET_INFO_ERROR = "필수 정보 제공에 실패했습니다. 다시 시도해주세요."
    const val SURVEY_ERROR = "설문 참여에 실패했습니다. 다시 시도해주세요."
    const val IMAGE_NULL_ERROR = "선택된 이미지가 없습니다."
    const val INVALID_SURVEY_ERROR = "존재하지 않는 설문입니다."
    const val NOT_ALLOW_SURVEY_ERROR = "참여할 수 없는 설문입니다."
    const val DID_SURVEY_ERROR = "이미 참여한 설문입니다."
    const val DATA_ERROR = "데이터를 불러오는데 실패했습니다."
    const val EDIT_ERROR = "데이터 수정에 실패했습니다."
    const val SURVEY_EDIT_ERROR = "제출화면 변경에 실패하였습니다."
    const val SURVEY_EDIT_DONE_ERROR = "응답 수집이 종료된 설문입니다."
    const val EXIST_LOGIN_ERROR = "계정 연동에 실패했습니다. 다시 시도해주세요."
    const val EXIST_LOGIN_NULL_ERROR = "아이디와 비밀번호를 정확하게 입력해주세요."
    const val EXIST_LOGIN_ERROR_TWICE = "계정 연동에 실패했습니다. 카카오 로그인을 이용해주세요."
    const val UNKNOWN_ERROR = "최초 회원가입인 경우, 카카오 로그인 이용해주세요."
    const val WITHDRAW_ERROR = "회원 탈퇴에 실패했습니다. 다시 시도해주세요."
    const val NETWORK_ERROR = "네트워크 통신에 실패했습니다."
    const val RETRY = "재시도"
}

object ErrorCode {
    const val CODE_400 = 400
    const val CODE_404 = 404
    const val CODE_409 = 409
}