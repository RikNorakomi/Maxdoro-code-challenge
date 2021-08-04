package com.rikvanvelzen.md_test.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rikvanvelzen.md_test.data.IRijksMuseumRepository
import com.rikvanvelzen.md_test.data.Result
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import com.rikvanvelzen.md_test.ui.Event
import kotlinx.coroutines.launch

class DetailScreenViewModel(private val repository: IRijksMuseumRepository) : ViewModel() {

    private val _detailInformation: MutableLiveData<Event<Result<ArtObjectDetails>>> = MutableLiveData<Event<Result<ArtObjectDetails>>>()
    val detailInformation: LiveData<Event<Result<ArtObjectDetails>>> = _detailInformation

    fun loadDetailedInformation(objectNumber: String) = viewModelScope.launch {
        val artObjectDetails = repository.getArtObjectDetails(objectNumber)
        _detailInformation.value = Event(artObjectDetails)
    }
}