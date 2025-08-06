package com.alkursi.presentation.feature.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alkursi.design.generic.component.AppLoaderScreen
import com.alkursi.design.generic.component.AppToolbar
import com.alkursi.presentation.R
import com.alkursi.presentation.common.component.NoArticlesContent
import com.alkursi.presentation.feature.details.component.NewsDetailsContent
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
internal fun NewsDetailsScreen(
    navController: NavController,
    articleId: Int,
    viewModel: NewsDetailsViewModel = koinViewModel(){
        parametersOf(articleId)
    }
) {

    val state = viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding(),
        topBar = {
            AppToolbar(
                title = stringResource(R.string.more_infos),
                onCliclk = { navController.navigateUp() })
        }
    ) { innerPadding ->

        val context = LocalContext.current

        when (val currentState = state.value) {
            is NewsDetailsState.Loaded -> NewsDetailsContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                article = currentState.article,
                context = context
            )

            is NewsDetailsState.Loading -> AppLoaderScreen()

            is NewsDetailsState.ArticleUnavailable -> NoArticlesContent()
        }


    }
}

@Composable
@PreviewLightDark
private fun NewsDetailsScreenPreview() {
    NewsDetailsScreen(
        navController = NavController(context = LocalContext.current),
        articleId = 1,
    )
}
