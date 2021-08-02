package com.rikvanvelzen.md_test.data

import androidx.paging.PagingData
import com.rikvanvelzen.md_test.model.ArtObject
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import kotlinx.coroutines.flow.Flow


interface IRijksMuseumRepository {
    fun getArtObjectsSearchResultStream(query: String): Flow<PagingData<ArtObject>>

    suspend fun getArtObjectDetails(objectNumber: String): Result<ArtObjectDetails>
}