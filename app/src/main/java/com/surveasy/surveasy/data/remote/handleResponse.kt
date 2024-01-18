package com.surveasy.surveasy.data.remote

import android.util.Log
import com.google.gson.Gson
import com.surveasy.surveasy.domain.base.BaseState
import com.surveasy.surveasy.domain.base.ServerCode
import com.surveasy.surveasy.domain.base.ServerCode.EXCEPTION
import com.surveasy.surveasy.domain.base.ServerCode.NULL
import com.surveasy.surveasy.domain.base.StatusCode
import retrofit2.Response

suspend fun <T> handleResponse(block: suspend () -> Response<T>): BaseState<T> {
    return try {
        val response = block()
        if (response.isSuccessful) {
            response.body()?.let {
                BaseState.Success(it)
            } ?: BaseState.Error(StatusCode.EMPTY, NULL)
        } else {
            BaseState.Error(StatusCode.ERROR, response.code())
        }
    } catch (e: Exception) {
        BaseState.Error(StatusCode.EXCEPTION, EXCEPTION)
    }
}