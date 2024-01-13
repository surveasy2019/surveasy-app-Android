package com.surveasy.surveasy.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.kakao.sdk.common.KakaoSdk
import com.surveasy.surveasy.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Surveasy")
        lateinit var instance: GlobalApplication
        fun getContext(): Context = instance.applicationContext
    }
}