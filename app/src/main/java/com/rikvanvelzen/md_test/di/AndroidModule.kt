package com.rikvanvelzen.md_test.di

import android.content.Context
import com.rikvanvelzen.md_test.ui.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AndroidModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context) = ResourceProvider(context)
}