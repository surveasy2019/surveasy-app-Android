package com.surveasy.surveasy.data.config

import android.util.Log
import com.surveasy.surveasy.app.DataStoreManager
import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.remote.runRemote
import com.surveasy.surveasy.domain.base.BaseState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Inject

class BearerInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        var newAccessToken: String? = null

        if (response.code == 500) {
            runBlocking {
                val refreshToken = dataStoreManager.getRefreshToken().first()
                refreshToken?.let { token ->
                    when (val result = getNewAccessToken(token)) {
                        is BaseState.Success -> {
                            response.close()
                            newAccessToken = result.data
                            newAccessToken?.let {
                                dataStoreManager.putAccessToken(newAccessToken!!)
                            }
                        }

                        else -> {
                            Log.d("TEST", "발급 실패")
//                            dataStoreManager.deleteAccessToken()
//                            dataStoreManager.deleteRefreshToken()
//
//                            val intent = Intent(App.getContext(), IntroActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                            App.getContext().startActivity(intent)
                        }
                    }
                }
            }
            newAccessToken?.let {
                val newRequest = originalRequest.newBuilder()
                    .addHeader(AUTHORIZATION, "$BEARER $newAccessToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }


    private suspend fun getNewAccessToken(refreshToken: String?): BaseState<String> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val api = retrofit.create(SurveasyApi::class.java)
        return runRemote { api.getTempToken() }
    }

    companion object {
        const val TOKEN_ERROR = 401
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer"
        const val BASE_URL = "https://gosurveasy.co.kr/"
    }
}