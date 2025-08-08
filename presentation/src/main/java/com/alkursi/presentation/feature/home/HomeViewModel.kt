package com.alkursi.presentation.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkursi.domain.news.GetArticlesUseCase
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.NewsError
import com.alkursi.presentation.feature.home.model.HomeEvent
import com.alkursi.presentation.feature.home.model.HomeState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val getArticlesUseCase: GetArticlesUseCase) : ViewModel() {

    private var currentPage = 1
    private val pageSize = 20
    private var totalResults = Int.MAX_VALUE

    private val articlesState = MutableStateFlow<List<Article>>(emptyList())
    private val isLoadingState = MutableStateFlow(false)
    private val isRefreshingState = MutableStateFlow(false)
    val state = combine(
        articlesState,
        isLoadingState,
        isRefreshingState
    ) { articles, isLoading, isRefreshing ->
        if (isLoading && articles.isEmpty()) {
            HomeState.Loading
        } else {
            HomeState.Loaded(articles = articles, isRefreshing = isRefreshing)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, HomeState.Loading)

    private val eventsChanel = Channel<HomeEvent>()
    val events = eventsChanel.receiveAsFlow()


    fun loadArticles() {
        viewModelScope.launch {
            runCatching {
                if (!canLoadArticles() || isLoadingState.getAndUpdate { true }) return@launch

                val headlines = getArticlesUseCase(page = currentPage, pageSize = pageSize)
                totalResults = headlines.articlesSize
                currentPage++
                isLoadingState.update { false }
                articlesState.update { currentArticles -> currentArticles + headlines.articles }
            }.onFailure { error ->
                isLoadingState.update { false }
                when (error) {
                    //todo send for bottomsheet
                    is NewsError.TechnicalError -> eventsChanel.send(HomeEvent.TechnicalError)
                    is NewsError.DataUnavailable -> eventsChanel.send(HomeEvent.DataUnavailable)
                    is NewsError.ServiceUnavailable -> eventsChanel.send(HomeEvent.ServiceUnavailable)
                }
            }
        }
    }

    fun refresh(){
        if(isRefreshingState.getAndUpdate { true }) return
        loadArticles()
        isRefreshingState.update { false }
    }

    private fun canLoadArticles(): Boolean = (currentPage - 1) * pageSize < totalResults

    fun navigateToArticleDetails(article: Article) {
        eventsChanel.trySend(HomeEvent.NavigateToArticleDetails(article))
    }
}