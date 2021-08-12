package com.rikvanvelzen.coding_challenge.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * Class that should be used to inject into ViewModel constructors
 * to provide Strings from string resources.
 * Use this provider instead of passing a context directly to the view model to:
 * 1. Provide memory leaks
 * 2. Keep view models free of Android framework related components
 *    so you don't need run instrumentation tests to test them
 */
class ResourceProvider(private val context: Context) {

    fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
        return context.getString(id, *formatArgs)
    }
}
