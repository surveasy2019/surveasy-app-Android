package com.surveasy.surveasy.data.config

import android.util.Log
import com.surveasy.surveasy.presentation.util.Temp
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor():
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder: Request.Builder = chain.request().newBuilder()
        builder.addHeader(AUTHORIZATION, "$BEARER ${Temp.temp}")
//        accessToken?.takeIf { it.isNotEmpty() }?.let {
//            builder.addHeader(AUTHORIZATION, "$BEARER $it")
//        }
        return chain.proceed(builder.build())
    }

    companion object {
        const val AUTHORIZATION = "authorization"
        const val BEARER = "Bearer"
    }
}