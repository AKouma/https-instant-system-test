package com.alkursi.presentation.feature.details

import com.alkursi.domain.news.model.Article

sealed class NewsDetailsState {

    data class Loaded(val article: Article) : NewsDetailsState()

    data object ArticleUnavailable : NewsDetailsState()

    data object Loading : NewsDetailsState()
}