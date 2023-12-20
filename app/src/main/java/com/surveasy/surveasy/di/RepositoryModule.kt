package com.surveasy.surveasy.di

import com.surveasy.surveasy.data.remote.SurveasyApi
import com.surveasy.surveasy.data.repository.PanelRepositoryImpl
import com.surveasy.surveasy.domain.repository.PanelRepository
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
    fun providePanelRepository(api: SurveasyApi): PanelRepository =
        PanelRepositoryImpl(api)
}