package com.rikvanvelzen.md_test.ui

import android.content.Context
import androidx.annotation.StringRes

class ResourceProvider(private val context: Context) {

    fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
        return context.getString(id, *formatArgs)
    }
}
