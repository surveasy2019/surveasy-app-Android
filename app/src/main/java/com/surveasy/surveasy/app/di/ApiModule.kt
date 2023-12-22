package com.surveasy.surveasy.app.di

import com.surveasy.surveasy.data.remote.SurveasyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideIntroService(retrofit: Retrofit): SurveasyApi = retrofit.create(SurveasyApi::class.java)
}