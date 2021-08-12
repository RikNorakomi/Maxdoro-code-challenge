package com.rikvanvelzen.coding_challenge.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rikvanvelzen.coding_challenge.common.MainCoroutineRule
import com.rikvanvelzen.coding_challenge.common.getOrAwaitValue
import com.rikvanvelzen.coding_challenge.data.repository.RijksMuseumRepository
import com.rikvanvelzen.coding_challenge.model.ArtObjectDetails
import com.rikvanvelzen.coding_challenge.model.Dating
import com.rikvanvelzen.coding_challenge.model.ImageDetails
import com.rikvanvelzen.coding_challenge.ui.detail.DetailScreenViewModel
import io.mockk.coEvery
import com.rikvanvelzen.coding_challenge.utils.Result
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailScreenViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val rijksMuseumRepo = mockk<RijksMuseumRepository>()
    private lateinit var viewModel: DetailScreenViewModel

    @Before
    fun setup() {
        viewModel = DetailScreenViewModel(rijksMuseumRepo)
    }

    @Test
    fun loadDetailedInformation_success_detailInfoLiveDataUpdated() {
        // Arrange / Given
        val objectNumber = "someId"
        val artObjectDetails = createArtObjectDetails(objectNumber)

        coEvery { rijksMuseumRepo.getArtObjectDetails(objectNumber) } returns Result.Success(
            artObjectDetails
        )

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
        coEvery { rijksMuseumRepo.getArtObjectDetails(objectNumber) } returns Result.Error(Exception())

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