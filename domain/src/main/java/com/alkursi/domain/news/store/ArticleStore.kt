package com.alkursi.domain.news.store

import com.alkursi.domain.news.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleStore {
    fun getAllArticles(): Flow<List<Article>>
    suspend fun getArticleById(id: Int): Article?
    suspend fun saveArticle(article: Article)
    suspend fun saveArticles(articles: List<Article>)
}