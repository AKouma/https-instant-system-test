package com.alkursi.presentation.feature.home.model

import com.alkursi.domain.news.model.Article

sealed class HomeState {

    data object Loading : HomeState()
    data object NoArticles : HomeState()
    data class Loaded(val articles: List<Article>) : HomeState()
}