package com.alkursi.domain.news.model

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    private val publishedAt: String?,
    val content: String?
) {
    val publishedAtDateTime: OffsetDateTime?
        get() = publishedAt.takeIf { !it.isNullOrEmpty() }
            ?.let { runCatching { OffsetDateTime.parse(it) }.getOrNull() }
}
