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

class RijksMuseumRepository(
    private val service: RijksMuseumService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getArtObjectsSearchResultStream(query: String): Flow<PagingData<ArtObject>> {
        Log.d("RijksMuseumRepository", "New query: $query")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { RijksMuseumPagingSource(service, query) }
        ).flow
    }

    // suspend function enforces the function to be called from a coroutine
    suspend fun getArtObjectDetails(objectNumber: String): Result<ArtObjectDetails> {

        // Moving the execution of the coroutine to be dispatch to an
        // IO thread makes the function main-safe
        // Additionally: as best practice don't hard code dispatchers but inject them via constructor
        return withContext(ioDispatcher) {
            try {
                Result.Success(service.fetchCollectionDetails(objectNumber).artObjectDetails)
            } catch (e: Exception) {
                // For the sake of simplicity for this code challenge we'll just return
                Result.Error(e)
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}