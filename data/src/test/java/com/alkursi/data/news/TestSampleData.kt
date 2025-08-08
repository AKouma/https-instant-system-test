package com.alkursi.data.news

import com.alkursi.data.news.model.ArticleDto
import com.alkursi.data.news.model.HeadlinesDto
import com.alkursi.data.news.model.SourceDto
import com.alkursi.data.news.model.Status

object TestSampleData {

    object Sources {
        val sourceWithId = SourceDto(
            id = "bbc-news",
            name = "BBC News"
        )

        val sourceWithoutId = SourceDto(
            id = null,
            name = "Unknown Source"
        )

        val sourceWithEmptyName = SourceDto(
            id = "test-id",
            name = ""
        )

        val basicSource = SourceDto(
            id = "source-id",
            name = "Source Name"
        )

        val testSource1 = SourceDto(
            id = "source-1",
            name = "Source One"
        )

        val testSource2 = SourceDto(
            id = null,
            name = "Source Two"
        )

        val genericTestSource = SourceDto(
            id = "test",
            name = "Test Source"
        )
    }

    object Articles {
        val completeArticle = ArticleDto(
            source = Sources.basicSource,
            author = "John Doe",
            title = "Test Title",
            description = "Test Description",
            url = "https://test.com",
            urlToImage = "https://test.com/image.jpg",
            publishedAt = "2023-12-01T10:30:00Z",
            content = "Test Content"
        )

        val articleWithAllFields = ArticleDto(
            source = Sources.sourceWithId,
            author = "Jane Smith",
            title = "Breaking News",
            description = "This is a test description",
            url = "https://example.com/article",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = "2023-12-15T14:30:00Z",
            content = "Full article content here..."
        )

        val articleWithNullFields = ArticleDto(
            source = Sources.sourceWithoutId,
            author = null,
            title = "Title Only",
            description = null,
            url = "https://example.com",
            urlToImage = null,
            publishedAt = "",
            content = null
        )

        val articleWithInvalidDate = ArticleDto(
            source = Sources.basicSource,
            author = "Author",
            title = "Title",
            description = "Description",
            url = "https://example.com",
            urlToImage = null,
            publishedAt = "invalid-date-format",
            content = null
        )

        val articleWithTimezone = ArticleDto(
            source = Sources.basicSource,
            author = "Author",
            title = "Title",
            description = "Description",
            url = "https://example.com",
            urlToImage = null,
            publishedAt = "2023-12-15T14:30:00+02:00",
            content = null
        )

        val olderArticle = ArticleDto(
            source = Sources.basicSource,
            author = "Author 2",
            title = "Title 2",
            description = "Description 2",
            url = "https://test2.com",
            urlToImage = null,
            publishedAt = "2023-12-01T10:00:00Z",
            content = null
        )

        val newerArticle = ArticleDto(
            source = Sources.basicSource,
            author = "Author 1",
            title = "Title 1",
            description = "Description 1",
            url = "https://test1.com",
            urlToImage = null,
            publishedAt = "2023-12-03T10:00:00Z",
            content = null
        )

        val articleWithDate = ArticleDto(
            source = Sources.basicSource,
            author = "Author 1",
            title = "With Date",
            description = null,
            url = "https://test1.com",
            urlToImage = null,
            publishedAt = "2023-12-01T10:00:00Z",
            content = null
        )

        val articleWithoutDate = ArticleDto(
            source = Sources.basicSource,
            author = "Author 2",
            title = "Without Date",
            description = null,
            url = "https://test2.com",
            urlToImage = null,
            publishedAt = "",
            content = null
        )

        val integrationArticle1 = ArticleDto(
            source = Sources.testSource1,
            author = "Author One",
            title = "First Article",
            description = "First description",
            url = "https://first.com",
            urlToImage = "https://first.com/image.jpg",
            publishedAt = "2023-12-02T10:00:00Z",
            content = "First content"
        )

        val integrationArticle2 = ArticleDto(
            source = Sources.testSource2,
            author = null,
            title = "Second Article",
            description = null,
            url = "https://second.com",
            urlToImage = null,
            publishedAt = "2023-12-01T10:00:00Z",
            content = null
        )


        fun generateArticleList(count: Int): List<ArticleDto> {
            return (1..count).map { index ->
                ArticleDto(
                    source = Sources.genericTestSource,
                    author = "Author $index",
                    title = "Title $index",
                    description = "Description $index",
                    url = "https://test$index.com",
                    urlToImage = null,
                    publishedAt = "2023-12-${generateDay(index)}T10:00:00Z",
                    content = null
                )
            }
        }
    }

    private fun generateDay(index: Int): String {
        val day = when {
            index < 1 -> 1
            index > 31 -> 31
            else -> index
        }
        return day.toString().padStart(2, '0')
    }


    object Headlines {
        val singleArticleHeadlines = HeadlinesDto(
            status = Status.OK,
            articlesSize = 1,
            articles = listOf(Articles.completeArticle)
        )

        val emptyHeadlines = HeadlinesDto(
            status = Status.OK,
            articlesSize = 0,
            articles = emptyList()
        )

        val unsortedHeadlines = HeadlinesDto(
            status = Status.OK,
            articlesSize = 2,
            articles = listOf(Articles.newerArticle, Articles.olderArticle) // Ordre inverse chronologique
        )

        val headlinesWithNullDates = HeadlinesDto(
            status = Status.OK,
            articlesSize = 2,
            articles = listOf(Articles.articleWithoutDate, Articles.articleWithDate)
        )

        val integrationHeadlines = HeadlinesDto(
            status = Status.OK,
            articlesSize = 2,
            articles = listOf(Articles.integrationArticle1, Articles.integrationArticle2)
        )

        fun generateLargeHeadlines(count: Int): HeadlinesDto {
            return HeadlinesDto(
                status = Status.OK,
                articlesSize = count,
                articles = Articles.generateArticleList(count)
            )
        }
    }

    object Collections {
        val allSources = listOf(
            Sources.sourceWithId,
            Sources.sourceWithoutId,
            Sources.sourceWithEmptyName,
            Sources.basicSource
        )

        val articlesForSorting = listOf(
            Articles.newerArticle,
            Articles.olderArticle,
            Articles.articleWithoutDate
        )

        val articlesWithVariousFields = listOf(
            Articles.articleWithAllFields,
            Articles.articleWithNullFields,
            Articles.articleWithInvalidDate,
            Articles.articleWithTimezone
        )

        val integrationArticles = listOf(
            Articles.integrationArticle1,
            Articles.integrationArticle2
        )
    }

    object Constants {
        const val VALID_DATE_UTC = "2023-12-15T14:30:00Z"
        const val VALID_DATE_WITH_TIMEZONE = "2023-12-15T14:30:00+02:00"
        const val INVALID_DATE_FORMAT = "invalid-date-format"
        const val EMPTY_DATE = ""

        const val TEST_URL = "https://example.com"
        const val TEST_IMAGE_URL = "https://example.com/image.jpg"

        const val LARGE_DATASET_SIZE = 100
        const val MEDIUM_DATASET_SIZE = 10
        const val SMALL_DATASET_SIZE = 3
    }

    object Builders {
        fun createArticleDto(
            sourceId: String? = "default-source",
            sourceName: String = "Default Source",
            author: String? = "Default Author",
            title: String = "Default Title",
            description: String? = "Default Description",
            url: String = "https://default.com",
            urlToImage: String? = "https://default.com/image.jpg",
            publishedAt: String = "2023-12-01T10:00:00Z",
            content: String? = "Default Content"
        ): ArticleDto {
            return ArticleDto(
                source = SourceDto(id = sourceId, name = sourceName),
                author = author,
                title = title,
                description = description,
                url = url,
                urlToImage = urlToImage,
                publishedAt = publishedAt,
                content = content
            )
        }

        fun createHeadlinesDto(
            status: Status = Status.OK,
            articlesSize: Int,
            articles: List<ArticleDto>
        ): HeadlinesDto {
            return HeadlinesDto(
                status = status,
                articlesSize = articlesSize,
                articles = articles
            )
        }
    }
}