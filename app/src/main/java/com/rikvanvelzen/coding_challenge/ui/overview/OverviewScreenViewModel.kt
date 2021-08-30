package com.rikvanvelzen.coding_challenge.ui.overview

import androidx.annotation.StringRes
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.rikvanvelzen.coding_challenge.R
import com.rikvanvelzen.coding_challenge.data.repository.RijksMuseumRepository
import com.rikvanvelzen.coding_challenge.model.ArtObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class OverviewScreenViewModel @Inject constructor(
    private val repository: RijksMuseumRepository
) : ViewModel() {

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null

    fun getListData(): Flow<PagingData<UiModel>> {

        // For the sake of this coding challenges I've hard-coded the query here
        // Which is usually of course a no-no
        val queryString = "Rembrandt van Rijn"

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
                            return@insertSeparators UiModel.SeparatorItem(
                                R.string.overview_header_x_letters_in_title,
                                after.titleCount
                            )
                        }
                        // check between 2 items
                        if (before.titleCount > after.titleCount) {
                            if (after.titleCount >= 1) {
                                UiModel.SeparatorItem(
                                    R.string.overview_header_x_letters_in_title,
                                    after.titleCount
                                )
                            } else {
                                UiModel.SeparatorItem(R.string.overview_header_no_title_available)
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

    // Storing the Resource ID here instead of getting the string directly has some advantages:
    // On Locale/config changes the string wouldn't be updated as the view model isn't recreated
    // But view re-creation is executed so having the view get the String from resources does update
    // correctly on locale change
    // Secondly having just an ID and no reference to Context (e.g. context.getString(...)) makes it
    // easier to unit test view models
    data class SeparatorItem(
        @StringRes val descriptionResId: Int,
        val numberOfItems: Int? = null
    ) : UiModel()
}

private val UiModel.ArtObjectItem.titleCount: Int
    get() = this.artObject.title.length