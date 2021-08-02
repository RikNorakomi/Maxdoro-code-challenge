package com.rikvanvelzen.md_test.data

import androidx.paging.PagingData
import com.rikvanvelzen.md_test.model.ArtObject
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import kotlinx.coroutines.flow.Flow

class FakeRijksMuseumRepository : IRijksMuseumRepository {

    private var artObjectDetailsMap: LinkedHashMap<String, ArtObjectDetails> = LinkedHashMap()
    private var shouldReturnError = false

    fun setArtObjectDetails(artObjectDetails: ArtObjectDetails){
        artObjectDetailsMap[artObjectDetails.objectNumber] = artObjectDetails
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun getArtObjectsSearchResultStream(query: String): Flow<PagingData<ArtObject>> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtObjectDetails(objectNumber: String): Result<ArtObjectDetails> {
        if (shouldReturnError) {
            return Result.Error(Exception("Testing api returning error"))
        }
        artObjectDetailsMap[objectNumber]?.let {
            return Result.Success(it)
        }
        return Result.Error(Exception("Could not find Art Object Details"))
    }
}