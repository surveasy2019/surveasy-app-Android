package com.surveasy.surveasy.domain.base

enum class StatusCode{
    EMPTY,
    ERROR,
    EXCEPTION
}

object ServerCode{
    const val NULL = -1
    const val EXCEPTION = -2
}

sealed class BaseState<out T> {
    data class Success<out T>(val data: T) : BaseState<T>()
    data class Error(val statusCode: StatusCode, val code: Int) : BaseState<Nothing>()
}