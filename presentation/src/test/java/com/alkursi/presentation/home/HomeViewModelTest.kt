package com.alkursi.presentation.home

import app.cash.turbine.turbineScope
import com.alkursi.config.test.AppTest
import com.alkursi.domain.news.GetArticlesUseCase
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Headlines
import com.alkursi.domain.news.model.NewsError
import com.alkursi.domain.news.model.Source
import com.alkursi.presentation.feature.home.HomeViewModel
import com.alkursi.presentation.feature.home.model.HomeEvent
import com.alkursi.presentation.feature.home.model.HomeState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertEquals

class HomeViewModelTest : AppTest() {

    @MockK
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    private lateinit var viewModel: HomeViewModel

    private val fakeArticles = listOf(
        Article(
            source = Source(id = "1", name = "Test"),
            author = "Author",
            title = "Title",
            description = "Description",
            url = "http://example.com",
            urlToImage = "http://example.com/image.png",
            publishedAt = "2025-08-08T18:00:00Z",
            content = "Content"
        )
    )

    override fun setup() {
        super.setup()
        viewModel = HomeViewModel(getArticlesUseCase)
    }

    @Test
    fun `loadArticles should update state to Loaded on success`() = runTest {
        turbineScope {
            coEvery { getArticlesUseCase(any(), any()) } returns Headlines(
                articlesSize = 1,
                articles = fakeArticles
            )

            val state = viewModel.state.testIn(backgroundScope)
            viewModel.loadArticles()

            assertEquals(HomeState.Loading, state.awaitItem())
            val loadedState = state.awaitItem()
            assertTrue(loadedState is HomeState.Loaded)
            assertEquals(fakeArticles, (loadedState as HomeState.Loaded).articles)
        }
    }

    @Test
    fun `loadArticles should emit ServiceUnavailable event on failure`() = runTest {
        turbineScope {
            coEvery { getArticlesUseCase(any(), any()) } throws NewsError.ServiceUnavailable

            val event = viewModel.events.testIn(backgroundScope)
            viewModel.loadArticles()

            assertEquals(HomeEvent.ServiceUnavailable, event.awaitItem())
        }
    }

    @Test
    fun `loadArticles should emit TechnicalError event on failure`() = runTest {
        turbineScope {
            coEvery { getArticlesUseCase(any(), any()) } throws NewsError.TechnicalError

            val event = viewModel.events.testIn(backgroundScope)
            viewModel.loadArticles()

            assertEquals(HomeEvent.TechnicalError, event.awaitItem())
        }
    }

    @Test
    fun `loadArticles should emit DataUnavailable event on failure`() = runTest {
        turbineScope {
            coEvery { getArticlesUseCase(any(), any()) } throws NewsError.DataUnavailable

            val event = viewModel.events.testIn(backgroundScope)
            viewModel.loadArticles()

            assertEquals(HomeEvent.DataUnavailable, event.awaitItem())
        }
    }

    @Test
    fun `refresh should reload articles and reset refreshing state`() = runTest {
        turbineScope {
            coEvery { getArticlesUseCase(any(), any()) } returns Headlines(
                articlesSize = 1,
                articles = fakeArticles
            )

            val state = viewModel.state.testIn(backgroundScope)
            viewModel.refresh()

            assertEquals(HomeState.Loading, state.awaitItem())
            val loadedState = state.awaitItem()
            assertTrue(loadedState is HomeState.Loaded)
            assertEquals(fakeArticles, (loadedState as HomeState.Loaded).articles)
            assertFalse(loadedState.isRefreshing)
        }
    }

    @Test
    fun `navigateToArticleDetails should emit navigation event`() = runTest {
        turbineScope {
            val article = fakeArticles.first()
            val event = viewModel.events.testIn(backgroundScope)

            viewModel.navigateToArticleDetails(article)

            val lastEvent = event.expectMostRecentItem()
            assertTrue(lastEvent is HomeEvent.NavigateToArticleDetails)
            assertEquals(article, (lastEvent as HomeEvent.NavigateToArticleDetails).article)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadArticles should not reload if already loading`() = runTest {
        turbineScope {
            coEvery { getArticlesUseCase(any(), any()) } coAnswers {
                delay(1000)
                Headlines(1, fakeArticles)
            }

            viewModel.loadArticles()
            viewModel.loadArticles()

            advanceTimeBy(1500)

            coVerify(exactly = 1) { getArticlesUseCase(any(), any()) }
        }
    }
}
