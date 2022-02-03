package com.example.surveasy

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplicationKakao : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,"c3dd0da30ec3cf75837fef9022ca48a4")
    }
}