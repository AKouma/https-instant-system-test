package com.alkursi.data.news.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.store.ArticleStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class ArticleStoreImpl(private val dataStore: DataStore<Preferences>) : ArticleStore {

    private val articlesKey = stringSetPreferencesKey("saved_articles")

    override fun getAllArticles(): Flow<List<Article>> {
        return dataStore.data.map { preferences ->
            val articlesJson = preferences[articlesKey] ?: emptySet()
            articlesJson.mapNotNull { json ->
                try {
                    Json.decodeFromString<Article>(json)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    override suspend fun getArticleById(id: Int): Article? {
        val preferences = dataStore.data.first()
        val articles = preferences[articlesKey] ?: emptySet()

        return articles.firstNotNullOfOrNull { current ->
            runCatching {
                val article = Json.decodeFromString<Article>(current)
                if (article.hashCode() == id) article else null
            }.onFailure {
                null
            }.getOrNull()
        }
    }

    override suspend fun saveArticle(article: Article) {
        val articleToSave = Json.encodeToString(article)
        dataStore.edit { preferences ->
            val currentArticles = preferences[articlesKey]?.toMutableSet() ?: mutableSetOf()
            currentArticles.removeIf { currentArticle ->
                Json.decodeFromString<Article>(currentArticle).hashCode() == article.hashCode()
            }
            currentArticles.add(articleToSave)
            preferences[articlesKey] = currentArticles
        }
    }

    override suspend fun saveArticles(articles: List<Article>) {
        val articlesJson = articles.map { article -> Json.encodeToString(article) }

        dataStore.edit { preferences ->
            val currentArticles = preferences[articlesKey]?.toMutableSet() ?: mutableSetOf()

            articles.forEach { article ->
                currentArticles.removeIf {
                    try {
                        Json.decodeFromString<Article>(it).hashCode() == article.hashCode()
                    } catch (e: Exception) {
                        false
                    }
                }
            }

            currentArticles.addAll(articlesJson)
            preferences[articlesKey] = currentArticles
        }
    }
}