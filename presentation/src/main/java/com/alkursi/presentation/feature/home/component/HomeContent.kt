package com.alkursi.presentation.feature.home.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alkursi.core.NO_OP
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.design.theme.FlashNewsTheme
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Source
import com.alkursi.presentation.R
import com.alkursi.presentation.common.component.HeadlineCard
import com.alkursi.presentation.common.component.SectionTitle
import java.time.OffsetDateTime

@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    loadArticles: () -> Unit,
    navigateToArticleDetails: (Article) -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(bottom = AppGlobalTheme.spacing.large)
    ) {
        items(articles.size) { index ->
            if (index == 0) {
                SectionTitle(
                    title = stringResource(R.string.breaking_news),
                    modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
                )
                Spacer(modifier = Modifier.size(AppGlobalTheme.spacing.small))
                HeadlineCard(articles[0], navigateToArticleDetails)
                if (articles.size > 1) {
                    Spacer(modifier = Modifier.size(AppGlobalTheme.spacing.normal))
                    SectionTitle(
                        title = stringResource(R.string.news_brief),
                        modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(AppGlobalTheme.spacing.small))
                NewsCard(articles[index], navigateToArticleDetails)
            }
            if (index == articles.lastIndex) {
                LaunchedEffect(key1 = true) {
                    loadArticles()
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun HomeContentPreview() = FlashNewsTheme {
    Surface {
        HomeContent(
            articles = listOf(
                Article(
                    title = "Sample Article 1",
                    content = "This is a sample article content for preview.",
                    urlToImage = null,
                    publishedAt = OffsetDateTime.now().toString(),
                    source = Source(
                        id = "source1",
                        name = "Sample Source"
                    ),
                    author = "author",
                    description = "description",
                    url = "https://example.com",
                ),
                Article(
                    title = "Sample Article 2",
                    content = "This is another sample article content for preview.",
                    urlToImage = null,
                    publishedAt = OffsetDateTime.now().minusDays(1).toString(),
                    source = Source(
                        id = "source1",
                        name = "Sample Source"
                    ),
                    author = "author",
                    description = "description",
                    url = "https://example.com",
                )
            ),
            loadArticles = { NO_OP },
            navigateToArticleDetails = { NO_OP },
        )
    }
}