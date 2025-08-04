package com.alkursi.presentation.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alkursi.design.generic.component.FNewsSettingsToolbar
import com.alkursi.domain.news.model.Article
import com.alkursi.presentation.common.AppRoutes
import com.alkursi.presentation.common.EventListener
import com.alkursi.presentation.feature.home.component.HomeContent
import com.alkursi.presentation.feature.home.component.HomeContentLoading
import com.alkursi.presentation.common.component.NoArticlesContent
import com.alkursi.presentation.feature.home.model.HomeEvent
import com.alkursi.presentation.feature.home.model.HomeState
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val events = viewModel.events

    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        topBar = { FNewsSettingsToolbar(onLongPress = { navigateToSettings(navController) }) })
    { innerPadding ->
        when (val homeState = state.value) {
            is HomeState.Loaded -> HomeContentLoaded(homeState, innerPadding, viewModel)

            is HomeState.Loading -> HomeContentLoading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )

            is HomeState.NoArticles -> NoArticlesContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }

    EventListener(events, navController)
}

@Composable
private fun HomeContentLoaded(
    homeState: HomeState.Loaded,
    innerPadding: PaddingValues,
    viewModel: HomeViewModel
) {
    if (homeState.articles.isNotEmpty()) {
        HomeContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            articles = homeState.articles,
            loadArticles = viewModel::loadArticles,
            navigateToArticleDetails = viewModel::navigateToArticleDetails
        )
    } else {
        NoArticlesContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
