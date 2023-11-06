package com.surveasy.surveasy.di

import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.repository.SignupRepository
import com.surveasy.surveasy.data.repository_impl.SignupRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideSignupRepository(surveasyApi: SurveasyApi): SignupRepository =
        SignupRepositoryImpl(surveasyApi)
}