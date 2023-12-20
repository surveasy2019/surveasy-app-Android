package com.surveasy.surveasy.data.remote

import com.google.gson.Gson
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.base.StatusCode
import retrofit2.Response

suspend fun <T> runRemote(block: suspend () -> Response<T>): BaseState<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it)
            } ?: BaseState.Error(StatusCode.EMPTY, "null")
        } else {
            val errorData = Gson().fromJson(response.errorBody()?.string(), BaseState.Error::class.java)
            BaseState.Error(StatusCode.ERROR, errorData.message)
        }
    } catch (e: Exception) {
        BaseState.Error(StatusCode.EXCEPTION, e.message.toString())
    }
}