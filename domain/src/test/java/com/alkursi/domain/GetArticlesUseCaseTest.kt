package com.alkursi.domain

import com.alkursi.config.test.AppTest
import com.alkursi.domain.country.CountryRepository
import com.alkursi.domain.country.model.CountryInfo
import com.alkursi.domain.news.GetArticlesUseCase
import com.alkursi.domain.news.NewsRepository
import com.alkursi.domain.news.model.Headlines
import com.alkursi.domain.news.model.NewsError
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class GetArticlesUseCaseTest : AppTest() {

    @MockK
    private lateinit var newsRepository: NewsRepository

    @MockK
    private lateinit var countryRepository: CountryRepository

    private lateinit var useCase: GetArticlesUseCase


    override fun setup() {
        super.setup()
        useCase = GetArticlesUseCase(newsRepository, countryRepository)
    }

    @Test
    fun `invoke should return headlines when repository returns success`() = runTest {
        val expectedCountryCode = "fr"
        val expectedHeadlines = Headlines(articlesSize = 1, articles = listOf())
        every { countryRepository.getCountryInfo() } returns CountryInfo(code = expectedCountryCode)
        coEvery {
            newsRepository.getTopHeadlines(
                country = expectedCountryCode,
                page = 1,
                pageSize = 10
            )
        } returns Result.success(expectedHeadlines)

        val result = useCase(page = 1, pageSize = 10)

        assertEquals(expectedHeadlines, result)
    }

    @Test
    fun `invoke should throw when repository returns failure`() = runTest {
        val expectedCountryCode = "us"
        val exception = NewsError.ServiceUnavailable

        every { countryRepository.getCountryInfo() } returns CountryInfo(code = expectedCountryCode)
        coEvery {
            newsRepository.getTopHeadlines(
                country = expectedCountryCode,
                page = 1,
                pageSize = 10
            )
        } returns Result.failure(exception)

        assertFailsWith<NewsError.ServiceUnavailable> {
            useCase(page = 1, pageSize = 10)
        }
    }
}