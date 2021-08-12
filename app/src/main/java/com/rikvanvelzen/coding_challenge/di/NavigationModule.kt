package com.rikvanvelzen.coding_challenge.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * 'Modules' are used to add bindings to Hilt
 * They provide dependencies for types that cannot be constructor-injected like:
 * - interfaces
 * - classes that require instantiation via a builder
 *
 * 'InstalledIn' in defines to which Android class the module is scoped. F.e.:
 * - Application container is associated with SingletonComponent
 * - Fragment container is associated with FragmentComponent.
 */
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

//    @Binds
//    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}