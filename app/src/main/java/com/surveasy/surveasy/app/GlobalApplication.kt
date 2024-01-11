package com.surveasy.surveasy.app

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GlobalApplication : Application() {
    init {
        instance = this
    }

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Surveasy")
        lateinit var instance: GlobalApplication
        fun getContext(): Context = instance.applicationContext
    }
}