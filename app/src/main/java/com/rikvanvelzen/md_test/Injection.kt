package com.rikvanvelzen.md_test

import androidx.lifecycle.ViewModelProvider
import com.rikvanvelzen.md_test.api.RijksMuseumService
import com.rikvanvelzen.md_test.data.RijksMuseumRepository
import com.rikvanvelzen.md_test.ui.ViewModelFactory

/**
 * Class that handles object creation.
 * Like this, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    private fun provideRijksMuseumRepository(): RijksMuseumRepository {
        return RijksMuseumRepository(RijksMuseumService.create())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideRijksMuseumRepository())
    }
}
