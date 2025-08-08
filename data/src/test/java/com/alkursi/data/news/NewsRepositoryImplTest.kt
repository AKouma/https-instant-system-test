package com.alkursi.data.news

import com.alkursi.config.test.AppTest
import com.alkursi.data.news.model.ArticleDto
import com.alkursi.data.news.model.HeadlinesDto
import com.alkursi.data.news.model.SourceDto
import com.alkursi.data.news.model.Status
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Headlines
import com.alkursi.domain.news.model.NewsError
import com.alkursi.domain.news.model.Source
import com.alkursi.domain.news.store.ArticleStore
import com.google.gson.JsonSyntaxException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
class NewsRepositoryImplTest: AppTest() {

    @MockK
    private lateinit var newsApi: NewsApi
    @MockK
    private lateinit var newsMapper: NewsMapper
    @RelaxedMockK
    private lateinit var articleStore: ArticleStore
    @MockK
    private lateinit var repository: NewsRepositoryImpl


    override fun setup() {
        super.setup()
        repository = NewsRepositoryImpl(newsApi, newsMapper, articleStore)
    }

    @Test
    fun `getTopHeadlines returns success when response is OK`() = runTest{
        val headlinesDto = createHeadlinesDto(Status.OK)
        val response = Response.success(headlinesDto)
        val mapped = createHeadlines()

        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } returns response
        every { newsMapper.mapToDomain(headlinesDto) } returns mapped

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isSuccess)
        assertEquals(mapped, result.getOrNull())
        coVerify { articleStore.saveArticles(mapped.articles) }
    }

    @Test
    fun `getTopHeadlines returns failure when status is MESSAGE`() = runTest {
        val response = Response.success(createHeadlinesDto(Status.MESSAGE))

        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } returns response

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isFailure)
        assertEquals(NewsError.ServiceUnavailable, result.exceptionOrNull())
    }

    @Test
    fun `getTopHeadlines returns failure when response is null`() = runTest {
        val response: Response<HeadlinesDto> = Response.success(null)

        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } returns response

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isFailure)
        assertEquals(NewsError.DataUnavailable, result.exceptionOrNull())
    }

    @Test
    fun `getTopHeadlines returns technical error on 401 or 403`() = runTest {
        val response: Response<HeadlinesDto> = Response.error(401, "".toResponseBody())

        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } returns response

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isFailure)
        assertEquals(NewsError.TechnicalError, result.exceptionOrNull())
    }

    @Test
    fun `getTopHeadlines returns service unavailable on 500`() = runTest {
        val response: Response<HeadlinesDto> = Response.error(500, "".toResponseBody())

        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } returns response

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isFailure)
        assertEquals(NewsError.ServiceUnavailable, result.exceptionOrNull())
    }

    @Test
    fun `getTopHeadlines handles HttpException`() = runTest {
        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } throws HttpException(
            Response.error<Any>(500, "".toResponseBody())
        )

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isFailure)
        assertEquals(NewsError.ServiceUnavailable, result.exceptionOrNull())
    }

    @Test
    fun `getTopHeadlines handles Json exceptions`() = runTest {
        coEvery { newsApi.getTopHeadlines(any(), any(), any()) } throws JsonSyntaxException("")

        val result = repository.getTopHeadlines("us", 1, 10)

        assertTrue(result.isFailure)
        assertEquals(NewsError.TechnicalError, result.exceptionOrNull())
    }

    @Test
    fun `getArticleById returns article from store`() = runTest {
        val article = mockk<Article>()
        coEvery { articleStore.getArticleById(1) } returns article

        val result = repository.getArticleById(1)

        assertEquals(article, result)
    }


    private fun createHeadlinesDto(status: Status): HeadlinesDto {
        return HeadlinesDto(
            status = status,
            articlesSize = 1,
            articles = listOf(
                ArticleDto(
                    source = SourceDto(name = "test"),
                    author = "author",
                    title = "title",
                    description = "desc",
                    url = "url",
                    urlToImage = "image",
                    publishedAt = "2025-08-07T18:00:00Z",
                    content = "content"
                )
            )
        )
    }

    private fun createHeadlines(): Headlines {
        return Headlines(
            articlesSize = 1,
            articles = listOf(
                Article(
                    source = Source(id = null, name = "test"),
                    author = "author",
                    title = "title",
                    description = "desc",
                    url = "url",
                    urlToImage = "image",
                    content = "content",
                    publishedAt = "2025-08-07T18:00:00Z"
                )
            )
        )
    }
}