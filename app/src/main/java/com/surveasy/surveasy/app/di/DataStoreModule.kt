package com.surveasy.surveasy.app.di

import android.content.Context
import com.surveasy.surveasy.app.GlobalApplication.Companion.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore
}