package com.rikvanvelzen.md_test.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.rikvanvelzen.md_test.R
import com.rikvanvelzen.md_test.data.RijksMuseumRepository
import com.rikvanvelzen.md_test.model.ArtObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OverviewScreenViewModel @Inject constructor(
    private val repository: RijksMuseumRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null

    fun getListData(queryString: String): Flow<PagingData<UiModel>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }

        currentQueryValue = queryString
        val newResult: Flow<PagingData<UiModel>> =
            repository.getArtObjectsSearchResultStream(queryString)
                .map { pagingData -> pagingData.map { UiModel.ArtObjectItem(it) } }
                .map {
                    it.insertSeparators<UiModel.ArtObjectItem, UiModel> { before, after ->
                        if (after == null) {
                            // we're at the end of the list
                            return@insertSeparators null
                        }

                        if (before == null) {
                            // we're at the beginning of the list
                            return@insertSeparators UiModel.SeparatorItem(resourceProvider.getString(R.string.overview_header_x_letters_in_title, after.titleCount))
                        }
                        // check between 2 items
                        if (before.titleCount > after.titleCount) {
                            if (after.titleCount >= 1) {
                                UiModel.SeparatorItem(resourceProvider.getString(R.string.overview_header_x_letters_in_title, after.titleCount))
                            } else {
                                UiModel.SeparatorItem(resourceProvider.getString(R.string.overview_header_no_title_available))
                            }
                        } else {
                            // no separator
                            null
                        }
                    }
                }
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

sealed class UiModel {
    data class ArtObjectItem(val artObject: ArtObject) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.ArtObjectItem.titleCount: Int
    get() = this.artObject.title.length