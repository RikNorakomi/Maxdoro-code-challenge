package com.rikvanvelzen.coding_challenge.di

import com.rikvanvelzen.coding_challenge.data.api.RijksMuseumService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    @Singleton
    fun provideRijksMuseumService(): RijksMuseumService {
        return RijksMuseumService.create()
    }
}