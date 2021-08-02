package com.rikvanvelzen.md_test.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rikvanvelzen.md_test.common.MainCoroutineRule
import com.rikvanvelzen.md_test.common.getOrAwaitValue
import com.rikvanvelzen.md_test.data.FakeRijksMuseumRepository
import com.rikvanvelzen.md_test.data.Result
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import com.rikvanvelzen.md_test.model.Dating
import com.rikvanvelzen.md_test.model.ImageDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RijksCollectionViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var rijksMuseumRepo: FakeRijksMuseumRepository
    private lateinit var viewModel: RijksCollectionViewModel

    @Before
    fun setup() {
        rijksMuseumRepo = FakeRijksMuseumRepository()
        viewModel = RijksCollectionViewModel(rijksMuseumRepo)
    }

    @Test
    fun loadDetailedInformation_success_detailInfoLiveDataUpdated() {
        // Arrange / Given
        val objectNumber = "someId"
        val artObjectDetails = createArtObjectDetails(objectNumber)
        rijksMuseumRepo.setArtObjectDetails(artObjectDetails)

        // When detail information gets loaded
        viewModel.loadDetailedInformation(objectNumber)


        // assert that detailInformation LiveData is updated with correct info
        val value = viewModel.detailInformation.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), `is`(Result.Success(artObjectDetails)))
    }

    @Test
    fun loadDetailedInformation_error_detailInfoValueIsError() {
        // Arrange / Given
        val objectNumber = "someId"
        val artObjectDetails = createArtObjectDetails(objectNumber)
        rijksMuseumRepo.setReturnError(true)
        rijksMuseumRepo.setArtObjectDetails(artObjectDetails)

        // When detail information gets loaded
        viewModel.loadDetailedInformation(objectNumber)


        // assert that detailInformation LiveData is updated with correct info
        val value = viewModel.detailInformation.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled() is Result.Error, `is`(true))
    }

    private fun createArtObjectDetails(objectNumber: String): ArtObjectDetails {
        return ArtObjectDetails(
            "id",
            objectNumber,
            "title",
            "subtitle",
            "description",
            "physicalMedium",
            listOf("usedMat"),
            ImageDetails("url"),
            "auth",
            Dating("date")
        )
    }
}