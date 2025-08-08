package com.alkursi.domain

import com.alkursi.config.test.AppTest
import com.alkursi.domain.news.GetArticleByIdUseCase
import com.alkursi.domain.news.NewsRepository
import com.alkursi.domain.news.model.Article
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetArticleByIdUseCaseTest : AppTest() {

    @MockK
    private lateinit var newsRepository: NewsRepository

    private lateinit var useCase: GetArticleByIdUseCase

    override fun setup() {
        super.setup()
        useCase = GetArticleByIdUseCase(newsRepository)
    }

    @Test
    fun `invoke returns article when found`() = runTest {
        val id = 123
        val expectedArticle = mockk<Article>()
        coEvery { newsRepository.getArticleById(id) } returns expectedArticle

        val result = useCase(id)

        assertEquals(expectedArticle, result)
        coVerify(exactly = 1) { newsRepository.getArticleById(id) }
    }

    @Test
    fun `invoke returns null when no article found`() = runTest {
        val id = 999
        coEvery { newsRepository.getArticleById(id) } returns null

        val result = useCase(id)

        assertNull(result)
        coVerify(exactly = 1) { newsRepository.getArticleById(id) }
    }
}