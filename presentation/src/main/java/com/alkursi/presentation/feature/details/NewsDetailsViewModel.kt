package com.alkursi.presentation.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkursi.domain.news.GetArticleByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailsViewModel(
    private val articleId: Int,
    private val getArticleUseCase: GetArticleByIdUseCase,
) : ViewModel() {

    private val newsDetailsState = MutableStateFlow<NewsDetailsState>(NewsDetailsState.Loading)
    val state =
        newsDetailsState.stateIn(viewModelScope, SharingStarted.Lazily, NewsDetailsState.Loading)

    init {
        loadArticle()
    }

    private fun loadArticle() {
        viewModelScope.launch {
            runCatching {
                getArticleUseCase(articleId)?.let { article ->
                    newsDetailsState.update { NewsDetailsState.Loaded(article) }
                } ?: newsDetailsState.update { NewsDetailsState.ArticleUnavailable }
            }.onFailure {
                newsDetailsState.update { NewsDetailsState.ArticleUnavailable }
            }
        }
    }

}