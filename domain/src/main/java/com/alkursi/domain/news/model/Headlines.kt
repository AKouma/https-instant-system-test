package com.alkursi.domain.news.model

import kotlinx.serialization.Serializable

@Serializable
data class Headlines(
    val articlesSize: Int,
    val articles: List<Article>
)
