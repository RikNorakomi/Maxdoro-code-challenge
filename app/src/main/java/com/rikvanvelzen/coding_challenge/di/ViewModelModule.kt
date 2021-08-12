package com.rikvanvelzen.coding_challenge.di

import com.rikvanvelzen.coding_challenge.data.api.RijksMuseumService
import com.rikvanvelzen.coding_challenge.data.repository.RijksMuseumRepository
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