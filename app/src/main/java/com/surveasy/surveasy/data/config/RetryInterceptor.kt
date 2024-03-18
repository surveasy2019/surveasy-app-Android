package com.surveasy.surveasy.data.config

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class RetryInterceptor @Inject constructor() :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var retryCount = 0
        val request = chain.request()
        var response = chain.proceed(request)

        while (response.code == RETRY_ERROR && retryCount < RETRY_MAX) {
            retryCount++
            response.close()
            response = chain.proceed(request)
        }

        return response

    }

    companion object {
        const val RETRY_ERROR = 400
        const val RETRY_MAX = 2
    }
}