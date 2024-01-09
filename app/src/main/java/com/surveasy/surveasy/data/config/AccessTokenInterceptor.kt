package com.surveasy.surveasy.data.config

import android.util.Log
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.presentation.util.Temp.temp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

//        val accessToken =  runBlocking {
//            dataStoreManager.getAccessToken().first()
//        }
//
//        Log.d("token",accessToken.toString())
        val builder: Request.Builder = chain.request().newBuilder()
//        accessToken?.takeIf { it.isNotEmpty() }?.let {
//        }
        builder.addHeader(AUTHORIZATION, "$BEARER $temp")
        return chain.proceed(builder.build())
    }

    companion object {
        const val AUTHORIZATION = "authorization"
        const val BEARER = "Bearer"
    }
}