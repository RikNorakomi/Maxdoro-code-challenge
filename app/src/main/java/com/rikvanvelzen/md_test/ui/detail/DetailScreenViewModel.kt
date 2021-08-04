package com.rikvanvelzen.md_test.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rikvanvelzen.md_test.data.Result
import com.rikvanvelzen.md_test.data.RijksMuseumRepository
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import com.rikvanvelzen.md_test.ui.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: RijksMuseumRepository
) : ViewModel() {

    private val _detailInformation: MutableLiveData<Event<Result<ArtObjectDetails>>> =
        MutableLiveData<Event<Result<ArtObjectDetails>>>()
    val detailInformation: LiveData<Event<Result<ArtObjectDetails>>> = _detailInformation

    fun loadDetailedInformation(objectNumber: String) = viewModelScope.launch {
        val artObjectDetails = repository.getArtObjectDetails(objectNumber)
        _detailInformation.value = Event(artObjectDetails)
    }
}