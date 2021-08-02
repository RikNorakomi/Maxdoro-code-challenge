package com.rikvanvelzen.md_test.ui

import com.rikvanvelzen.md_test.common.MainCoroutineRule
import com.rikvanvelzen.md_test.common.getOrAwaitValue
import com.rikvanvelzen.md_test.common.whenever
import com.rikvanvelzen.md_test.data.FakeRijksMuseumRepository
import com.rikvanvelzen.md_test.model.ArtObjectDetails
import com.rikvanvelzen.md_test.model.Dating
import com.rikvanvelzen.md_test.model.ImageDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
//@RunWith(MockitoJUnitRunner::class)
class RijksCollectionViewModelTest {


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var rijksMuseumRepo: FakeRijksMuseumRepository

    private lateinit var viewModel: RijksCollectionViewModel

    @Before
    fun setup() {
        rijksMuseumRepo = FakeRijksMuseumRepository()
        viewModel = RijksCollectionViewModel(rijksMuseumRepo)
    }

//    @Test
//    suspend fun getDetailInformation() {
//        // Arrange / Given
//        val objectNumber = "someId"
//        val artObjectDetails = createArtObjectDetails(objectNumber)
//        whenever(mockRepository.getArtObjectDetails(objectNumber))
//            .thenReturn(artObjectDetails)
//
//        // Act / When
//        viewModel.loadDetailedInformation(objectNumber)
//
//        // Assert / Then
//    }


    @Test
    fun loadDetailedInformation() {
        // Arrange / Given
        val objectNumber = "someId"
        val artObjectDetails = createArtObjectDetails(objectNumber)
        runBlocking {
            whenever(rijksMuseumRepo.getArtObjectDetails(objectNumber))
                .thenReturn(artObjectDetails)
            viewModel.loadDetailedInformation(objectNumber)
        }


        val value = viewModel.detailInformation.getOrAwaitValue()
        // Act / When
        assertThat(value.getContentIfNotHandled(), `is`(artObjectDetails))
    }

    // region helper methods
    private fun createArtObjectDetails(id: String): ArtObjectDetails {
        return ArtObjectDetails(
            id,
            "number",
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