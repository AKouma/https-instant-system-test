package com.alkursi.data.news

import android.os.Build
import androidx.annotation.RequiresApi
import com.alkursi.data.news.model.ArticleDto
import com.alkursi.data.news.model.HeadlinesDto
import com.alkursi.data.news.model.SourceDto
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Headlines
import com.alkursi.domain.news.model.Source

class NewsMapper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToDomain(headlinesDto: HeadlinesDto): Headlines = Headlines(
        articlesSize = headlinesDto.articlesSize,
        articles = headlinesDto.articles.map { mapToDomain(it) }.sortedBy { it.publishedAtDateTime }
    )


    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToDomain(articleDto: ArticleDto): Article {
        return Article(
            source = mapSourceToDomain(articleDto.source),
            author = articleDto.author,
            title = articleDto.title,
            description = articleDto.description,
            url = articleDto.url,
            urlToImage = articleDto.urlToImage,
            publishedAt = articleDto.publishedAt,
            content = articleDto.content
        )
    }

    private fun mapSourceToDomain(sourceDto: SourceDto): Source {
        return Source(
            id = sourceDto.id,
            name = sourceDto.name
        )
    }
}