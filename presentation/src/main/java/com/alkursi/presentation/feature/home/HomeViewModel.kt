package com.alkursi.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkursi.domain.country.CountryRepository
import com.alkursi.domain.news.GetArticlesUseCase
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.NewsError
import com.alkursi.presentation.feature.home.model.HomeEvent
import com.alkursi.presentation.feature.home.model.HomeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val getArticlesUseCase: GetArticlesUseCase) : ViewModel() {

    private var currentPage = 1
    private val pageSize = 20
    private var totalResults = Int.MAX_VALUE

    private val articlesState = MutableStateFlow<List<Article>>(emptyList())
    private val homeState = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = homeState.stateIn(viewModelScope, SharingStarted.Lazily, homeState)

    private val eventsChanel = Channel<HomeEvent>()
    val events = eventsChanel.receiveAsFlow()


    fun loadArticles() {
        viewModelScope.launch {
            runCatching {
                if (!canLoadArticles()) return@launch

                val headlines = getArticlesUseCase(page = currentPage, pageSize = pageSize)
                totalResults = headlines.articlesSize
                currentPage++
                articlesState.update { currentArticles -> currentArticles + headlines.articles }
                homeState.update { HomeState.Loaded(articles = articlesState.value) }
            }.onFailure { error ->
                homeState.update { HomeState.NoArticles }
                when (error) {
                    is NewsError.TechnicalError -> eventsChanel.send(HomeEvent.TechnicalError)
                    is NewsError.DataUnavailable -> eventsChanel.send(HomeEvent.DataUnavailable)
                    is NewsError.ServiceUnavailable -> eventsChanel.send(HomeEvent.ServiceUnavailable)
                }
            }
        }
    }

    private fun canLoadArticles(): Boolean = (currentPage - 1) * pageSize < totalResults

    fun resetPagination() {
        currentPage = 1
        totalResults = Int.MAX_VALUE
    }

    fun navigateToArticleDetails(article: Article) {
        eventsChanel.trySend(HomeEvent.NavigateToArticleDetails(article))
    }
}