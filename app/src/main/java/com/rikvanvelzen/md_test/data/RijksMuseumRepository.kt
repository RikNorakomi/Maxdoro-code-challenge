package com.rikvanvelzen.md_test.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rikvanvelzen.md_test.api.RijksMuseumService
import com.rikvanvelzen.md_test.model.ArtObject
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

// TODO Impl returning different result for error handling
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class RijksMuseumRepository(private val service: RijksMuseumService,
                            private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    fun getArtObjectsSearchResultStream(query: String): Flow<PagingData<ArtObject>> {
        Log.d("RijksMuseumRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { RijksMuseumPagingSource(service, query) }
        ).flow
    }

    // suspend function enforces the function to be called from a coroutine
    suspend fun getArtObjectDetails(objectNumber: String): ArtObjectDetails {

        // Moving the execution of the coroutine to be dispatch to an
        // IO thread makes the function main-safe
        // Additionally: as best practice don't hard code dispatchers but inject them via constructor
        return withContext(ioDispatcher) {
            // TODO error handling
            service.fetchCollectionDetails(objectNumber).artObjectDetails
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}