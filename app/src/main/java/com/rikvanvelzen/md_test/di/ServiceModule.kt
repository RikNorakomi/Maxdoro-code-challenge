package com.rikvanvelzen.md_test.di

import com.rikvanvelzen.md_test.data.api.RijksMuseumService
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