package com.surveasy.surveasy.data.config

import com.surveasy.surveasy.app.DataStoreManager
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

        val accessToken = runBlocking {
            dataStoreManager.getAccessToken().first()
        }

        val builder: Request.Builder = chain.request().newBuilder()
        accessToken?.takeIf { it.isNotEmpty() }?.let {
            builder.addHeader(AUTHORIZATION, "$BEARER $it")
        }
        return chain.proceed(builder.build())
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
    }
}