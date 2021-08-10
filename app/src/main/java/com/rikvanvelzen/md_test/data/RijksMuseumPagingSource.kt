package com.rikvanvelzen.md_test.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rikvanvelzen.md_test.data.api.RijksMuseumService
import com.rikvanvelzen.md_test.data.RijksMuseumRepository.Companion.NETWORK_PAGE_SIZE
import com.rikvanvelzen.md_test.data.model.ArtObject
import retrofit2.HttpException
import java.io.IOException

// Start index for Rijks API is 1
private const val RIJKS_API_STARTING_PAGE_INDEX = 1

class RijksMuseumPagingSource(
    private val service: RijksMuseumService,
    private val query: String
) : PagingSource<Int, ArtObject>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArtObject> {
        val position = params.key ?: RIJKS_API_STARTING_PAGE_INDEX
        val apiQuery = query
        return try {
            val response =
                service.fetchCollectionByInvolvedMaker(apiQuery, position, params.loadSize)
            val artObjects = response.artObjects
            val nextKey = if (artObjects.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = artObjects,
                prevKey = if (position == RIJKS_API_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
