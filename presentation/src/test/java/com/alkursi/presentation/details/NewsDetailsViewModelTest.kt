package com.alkursi.presentation.details

import app.cash.turbine.turbineScope
import com.alkursi.config.test.AppTest
import com.alkursi.domain.news.GetArticleByIdUseCase
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Source
import com.alkursi.presentation.feature.details.NewsDetailsState
import com.alkursi.presentation.feature.details.NewsDetailsViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals


class NewsDetailsViewModelTest : AppTest() {

    private lateinit var viewModel: NewsDetailsViewModel

    @MockK
    private lateinit var getArticleUseCase: GetArticleByIdUseCase

    private val testArticle = Article(
        source = Source("test-id", "Test Source"),
        author = "Test Author",
        title = "Test Title",
        description = "Test Description",
        url = "https://test.com",
        urlToImage = "https://test.com/image.jpg",
        publishedAt = "2025-08-08T12:00:00Z",
        content = "Test content"
    )

    @Test
    fun `should emit Loaded state when article is found`() = runTest {
        turbineScope {
            coEvery { getArticleUseCase(1) } returns testArticle

            viewModel = NewsDetailsViewModel(
                articleId = 1,
                getArticleUseCase = getArticleUseCase
            )

            val state = viewModel.state.testIn(backgroundScope)


            assertEquals(NewsDetailsState.Loading, state.awaitItem())
            assertEquals(NewsDetailsState.Loaded(testArticle), state.awaitItem())
        }
    }

    @Test
    fun `should emit ArticleUnavailable state when article is null`() = runTest {
        turbineScope {
            coEvery { getArticleUseCase(1) } returns null

            viewModel = NewsDetailsViewModel(
                articleId = 1,
                getArticleUseCase = getArticleUseCase
            )

            val state = viewModel.state.testIn(backgroundScope)


            assertEquals(NewsDetailsState.Loading, state.awaitItem())
            assertEquals(NewsDetailsState.ArticleUnavailable, state.awaitItem())
        }
    }

    @Test
    fun `should emit ArticleUnavailable state on exception`() = runTest {
        turbineScope {
            coEvery { getArticleUseCase(1) } throws RuntimeException("Unexpected error")

            viewModel = NewsDetailsViewModel(
                articleId = 1,
                getArticleUseCase = getArticleUseCase
            )

            val state = viewModel.state.testIn(backgroundScope)


            assertEquals(NewsDetailsState.Loading, state.awaitItem())
            assertEquals(NewsDetailsState.ArticleUnavailable, state.awaitItem())
        }
    }
}
