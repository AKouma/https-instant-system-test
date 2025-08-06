package com.alkursi.presentation.feature.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alkursi.design.generic.component.AppToolbar
import com.alkursi.domain.news.model.Article
import com.alkursi.presentation.common.AppRoutes
import com.alkursi.presentation.common.EventListener
import com.alkursi.presentation.common.component.NoArticlesContent
import com.alkursi.presentation.feature.home.component.HomeContent
import com.alkursi.presentation.feature.home.component.HomeContentLoading
import com.alkursi.presentation.feature.home.model.HomeEvent
import com.alkursi.presentation.feature.home.model.HomeState
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val events = viewModel.events

    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = { AppToolbar(onLongPress = { navigateToSettings(navController) }) })
    { innerPadding ->
        when (val homeState = state.value) {
            is HomeState.Loaded -> HomeContentLoaded(homeState, innerPadding, viewModel)

            is HomeState.Loading -> HomeContentLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }

    EventListener(events, navController)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContentLoaded(
    homeState: HomeState.Loaded,
    innerPadding: PaddingValues,
    viewModel: HomeViewModel
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeState.isRefreshing,
        onRefresh = viewModel::refresh
    )

    Box(modifier = Modifier.fillMaxSize()) {
        if (homeState.articles.isNotEmpty()) {
            HomeContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .pullRefresh(pullRefreshState),
                articles = homeState.articles,
                loadArticles = viewModel::loadArticles,
                navigateToArticleDetails = viewModel::navigateToArticleDetails
            )
        } else {
            NoArticlesContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
            )
        }
        PullRefreshIndicator(
            refreshing = homeState.isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun EventListener(
    events: Flow<HomeEvent>,
    navController: NavController
) {
    EventListener(event = events) {
        when (it) {
            is HomeEvent.NavigateToArticleDetails -> navigateToNewsDetails(
                navController,
                it.article
            )
        }
    }
}

private fun navigateToNewsDetails(
    navController: NavController,
    article: Article
) {
    navController.navigate(AppRoutes.NewsDetails.createRoute(articleId = article.hashCode()))
}


private fun navigateToSettings(navController: NavController) =
    navController.navigate(AppRoutes.Settings.routeName)
