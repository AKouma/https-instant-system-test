package com.alkursi.data.news

import com.alkursi.config.test.AppTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import java.time.OffsetDateTime
import kotlin.test.assertNull

class NewsMapperTest : AppTest() {

    private lateinit var mapper: NewsMapper

    override fun setup() {
        mapper = NewsMapper()
    }

    @Test
    fun `should map all fields correctly`() {
        val headlinesDto = TestSampleData.Headlines.singleArticleHeadlines

        val result = mapper.mapToDomain(headlinesDto)

        assertEquals(1, result.articlesSize)
        assertEquals(1, result.articles.size)
        assertEquals("Test Title", result.articles[0].title)
    }

    @Test
    fun `should handle empty articles list`() {
        val headlinesDto = TestSampleData.Headlines.emptyHeadlines

        val result = mapper.mapToDomain(headlinesDto)

        assertEquals(0, result.articlesSize)
        assertTrue(result.articles.isEmpty())
    }

    @Test
    fun `should sort articles by publishedAtDateTime ascending`() {
        val headlinesDto = TestSampleData.Headlines.unsortedHeadlines

        val result = mapper.mapToDomain(headlinesDto)

        assertEquals(2, result.articles.size)
        assertEquals("Title 2", result.articles[0].title)
        assertEquals("Title 1", result.articles[1].title)
    }

    @Test
    fun `should handle articles with null publishedAt in sorting`() {
        val headlinesDto = TestSampleData.Headlines.headlinesWithNullDates

        val result = mapper.mapToDomain(headlinesDto)

        assertEquals(2, result.articles.size)
        assertNull(result.articles[0].publishedAtDateTime)
        assertEquals("Without Date", result.articles[0].title)
    }


    @Test
    fun `should map all fields correctly with all values present`() {
        val articleDto = TestSampleData.Articles.articleWithAllFields

        val result = mapper.mapToDomain(articleDto)

        assertEquals("Jane Smith", result.author)
        assertEquals("Breaking News", result.title)
        assertEquals("This is a test description", result.description)
        assertEquals("https://example.com/article", result.url)
        assertEquals("https://example.com/image.jpg", result.urlToImage)
        assertEquals("Full article content here...", result.content)
        assertEquals(OffsetDateTime.parse("2023-12-15T14:30:00Z"), result.publishedAtDateTime)
    }

    @Test
    fun `should handle all nullable fields as null`() {
        val articleDto = TestSampleData.Articles.articleWithNullFields

        val result = mapper.mapToDomain(articleDto)

        assertNull(result.author)
        assertEquals("Title Only", result.title)
        assertNull(result.description)
        assertEquals("https://example.com", result.url)
        assertNull(result.urlToImage)
        assertNull(result.content)
        assertNull(result.publishedAtDateTime)
    }

    @Test
    fun `should handle invalid publishedAt date format`() {
        val articleDto = TestSampleData.Articles.articleWithInvalidDate

        val result = mapper.mapToDomain(articleDto)

        assertNull(result.publishedAtDateTime)
    }

    @Test
    fun `should handle different valid date formats`() {
        val articleDto = TestSampleData.Articles.articleWithTimezone

        val result = mapper.mapToDomain(articleDto)

        assertEquals(OffsetDateTime.parse("2023-12-15T14:30:00+02:00"), result.publishedAtDateTime)
    }

    @Test
    fun `should map source with id and name`() {
        val articleDto = TestSampleData.Builders.createArticleDto(
            sourceId = "bbc-news",
            sourceName = "BBC News"
        )

        val result = mapper.mapToDomain(articleDto).source

        assertEquals("bbc-news", result.id)
        assertEquals("BBC News", result.name)
    }

    @Test
    fun `should handle source with null id`() {
        val articleDto = TestSampleData.Builders.createArticleDto(
            sourceId = null,
            sourceName = "Unknown Source"
        )

        val result = mapper.mapToDomain(articleDto).source

        assertNull(result.id)
        assertEquals("Unknown Source", result.name)
    }

    @Test
    fun `should handle source with empty name`() {
        val articleDto = TestSampleData.Builders.createArticleDto(
            sourceId = "test-id",
            sourceName = ""
        )

        val result = mapper.mapToDomain(articleDto).source

        assertEquals("test-id", result.id)
        assertEquals("", result.name)
    }

    @Test
    fun `should map complete HeadlinesDto with multiple articles`() {
        val headlinesDto = TestSampleData.Headlines.integrationHeadlines

        val result = mapper.mapToDomain(headlinesDto)

        assertEquals(2, result.articlesSize)
        assertEquals(2, result.articles.size)

        assertEquals("Second Article", result.articles[0].title)
        assertEquals("First Article", result.articles[1].title)

        assertNull(result.articles[0].source.id)
        assertEquals("Source Two", result.articles[0].source.name)
        assertEquals("source-1", result.articles[1].source.id)
        assertEquals("Source One", result.articles[1].source.name)
    }

    @Test
    fun `should handle large number of articles`() {

        val headlinesDto = TestSampleData.Headlines.generateLargeHeadlines(
            TestSampleData.Constants.LARGE_DATASET_SIZE
        )

        val result = mapper.mapToDomain(headlinesDto)


        assertEquals(TestSampleData.Constants.LARGE_DATASET_SIZE, result.articlesSize)
        assertEquals(TestSampleData.Constants.LARGE_DATASET_SIZE, result.articles.size)

        assertEquals("Title 1", result.articles[0].title)
        assertEquals("Title 100", result.articles[99].title)
    }

    @Test
    fun `should maintain data integrity across mapping`() {
        val originalArticle = TestSampleData.Articles.articleWithAllFields
        val headlinesDto = TestSampleData.Builders.createHeadlinesDto(
            articlesSize = 1,
            articles = listOf(originalArticle)
        )

        val result = mapper.mapToDomain(headlinesDto)
        val mappedArticle = result.articles[0]

        assertEquals(originalArticle.author, mappedArticle.author)
        assertEquals(originalArticle.title, mappedArticle.title)
        assertEquals(originalArticle.description, mappedArticle.description)
        assertEquals(originalArticle.url, mappedArticle.url)
        assertEquals(originalArticle.urlToImage, mappedArticle.urlToImage)
        assertEquals(originalArticle.content, mappedArticle.content)
        assertEquals(originalArticle.source.id, mappedArticle.source.id)
        assertEquals(originalArticle.source.name, mappedArticle.source.name)
    }
}