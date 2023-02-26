package com.surveasy.surveasy.auth

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.surveasy.surveasy.R

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_nativeAppKey))
    }
}