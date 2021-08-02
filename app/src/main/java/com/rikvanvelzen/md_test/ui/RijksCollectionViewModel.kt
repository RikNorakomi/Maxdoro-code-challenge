package com.rikvanvelzen.md_test.ui

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.rikvanvelzen.md_test.data.RijksMuseumRepository
import com.rikvanvelzen.md_test.model.ArtObject
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RijksCollectionViewModel(private val repository: RijksMuseumRepository) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null

    private val _detailInformation: MutableLiveData<Event<ArtObjectDetails>> = MutableLiveData<Event<ArtObjectDetails>>()
    val detailInformation: LiveData<Event<ArtObjectDetails>> = _detailInformation

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
                            return@insertSeparators UiModel.SeparatorItem("${after.titleCount} letters in title")
                        }
                        // check between 2 items
                        if (before.titleCount > after.titleCount) {
                            if (after.titleCount >= 1) {
                                UiModel.SeparatorItem("${after.titleCount} letters in title")
                            } else {
                                UiModel.SeparatorItem("< 10.000+ stars")
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

    fun loadDetailedInformation(objectNumber: String) {
        viewModelScope.launch {
            val artObjectDetails = repository.getArtObjectDetails(objectNumber)
            _detailInformation.value = Event(artObjectDetails)
        }
    }
}

sealed class UiModel {
    data class ArtObjectItem(val artObject: ArtObject) : UiModel()
    data class SeparatorItem(val description: String) : UiModel()
}

private val UiModel.ArtObjectItem.titleCount: Int
    get() = this.artObject.title.length