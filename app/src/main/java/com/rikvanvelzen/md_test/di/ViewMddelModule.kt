package com.rikvanvelzen.md_test.di

import com.rikvanvelzen.md_test.api.RijksMuseumService
import com.rikvanvelzen.md_test.data.RijksMuseumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideRepo(service: RijksMuseumService) = RijksMuseumRepository(service)
}