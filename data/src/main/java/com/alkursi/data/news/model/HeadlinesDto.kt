package com.alkursi.data.news.model

import com.google.gson.annotations.SerializedName

data class HeadlinesDto(
    @SerializedName("status") val status: Status,
    @SerializedName("totalResults") val articlesSize: Int,
    @SerializedName("articles") val articles: List<ArticleDto>
)

enum class Status {
    @SerializedName("ok")
    OK,

    @SerializedName("error")
    ERROR,

    @SerializedName("message")
    MESSAGE
}
