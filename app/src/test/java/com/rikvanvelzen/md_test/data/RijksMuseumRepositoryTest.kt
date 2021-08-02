package com.rikvanvelzen.md_test.data

import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Test

class RijksMuseumRepositoryTest : TestCase() {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @Test
    fun testBookmarkArticle() {
//        // Execute all coroutines that use this Dispatcher immediately
//        testDispatcher.runBlockingTest {
//            val articlesDataSource = FakeArticlesDataSource()
//            val repository = ArticlesRepository(
//                articlesDataSource,
//                // Make the CoroutineScope use the same dispatcher
//                // that we use for runBlockingTest
//                CoroutineScope(testDispatcher),
//                testDispatcher
//            )
//            val article = Article()
//            repository.bookmarkArticle(article)
//            assertThat(articlesDataSource.isBookmarked(article)).isTrue()
//        }
//        // make sure nothing else is scheduled to be executed
//        testDispatcher.cleanupTestCoroutines()
    }
}