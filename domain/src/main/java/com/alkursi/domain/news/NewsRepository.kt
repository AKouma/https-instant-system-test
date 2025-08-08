package com.alkursi.domain.news

import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Headlines

interface NewsRepository {

    suspend fun getTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int,
    ): Result<Headlines>

    suspend fun getArticleById(id: Int): Article?
}