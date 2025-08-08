package com.alkursi.data.news.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.alkursi.config.test.AppTest
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Source
import com.alkursi.domain.news.store.ArticleStore
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.time.OffsetDateTime
import kotlin.test.assertEquals

class ArticleStoreImplTest : AppTest() {

    @MockK
    private lateinit var dataStore: DataStore<Preferences>
    @MockK
    private lateinit var articleStore: ArticleStore

    private val sampleArticle = Article(
        source = Source("1", "source"),
        author = "author",
        title = "title",
        description = "desc",
        url = "url",
        urlToImage = "image",
        content = "content",
        publishedAt = OffsetDateTime.now().toString()
    )

    override fun setup() {
        super.setup()
        dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = null,
            scope = CoroutineScope(testDispatcher + SupervisorJob()),
            produceFile = { File(context.filesDir, "test.preferences_pb") }
        )
        articleStore = ArticleStoreImpl(dataStore)
    }

    @Test
    fun `getAllArticles returns empty list when no articles saved`() = runTest(testDispatcher) {
        val result = articleStore.getAllArticles().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun `saveArticle then getArticleById returns correct article`() = runTest(testDispatcher) {
        articleStore.saveArticle(sampleArticle)
        val retrieved = articleStore.getArticleById(sampleArticle.hashCode())
        assertEquals(sampleArticle.title, retrieved?.title)
        assertEquals(sampleArticle.hashCode(), retrieved?.hashCode())
    }

    @Test
    fun `saveArticles then getAllArticles returns all articles`() = runTest(testDispatcher) {
        val article2 = sampleArticle.copy(title = "Another title")
        articleStore.saveArticles(listOf(sampleArticle, article2))

        val allArticles = articleStore.getAllArticles().first()
        assertEquals(2, allArticles.size)
        assertTrue(allArticles.any { it.title == "title" })
        assertTrue(allArticles.any { it.title == "Another title" })
    }

    @Test
    fun `saveArticle replaces existing article with same hash`() = runTest(testDispatcher) {
        articleStore.saveArticle(sampleArticle)
        articleStore.saveArticle(sampleArticle)

        val all = articleStore.getAllArticles().first()
        assertEquals(1, all.size)
    }

}