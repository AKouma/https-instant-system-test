package com.alkursi.presentation.feature.home.model

import com.alkursi.domain.news.model.Article
import com.alkursi.presentation.common.UIEvent

sealed class HomeEvent: UIEvent {
    data class NavigateToArticleDetails(val article: Article) : HomeEvent()
    data object TechnicalError : HomeEvent()
    data object DataUnavailable : HomeEvent()
    data object ServiceUnavailable : HomeEvent()
}