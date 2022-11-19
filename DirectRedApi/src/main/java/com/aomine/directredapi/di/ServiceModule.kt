package com.aomine.directredapi.di

import com.aomine.directredapi.data.service.RedApiClient
import com.aomine.directredapi.data.service.RedService
import com.lagradost.nicehttp.Requests
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideRedServices(redApiClient: RedApiClient, requests: Requests):RedService{
        return RedService(redApiClient, requests)
    }
}