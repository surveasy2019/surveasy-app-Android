package com.surveasy.surveasy.domain.base

enum class StatusCode{
    EMPTY,
    ERROR,
    EXCEPTION,
    ERROR_AUTH,
    ERROR_NONE
}

sealed class BaseState<out T> {
    data class Success<out T>(val data: T) : BaseState<T>()
    data class Error(val statusCode: StatusCode, val message: String) : BaseState<Nothing>()
}