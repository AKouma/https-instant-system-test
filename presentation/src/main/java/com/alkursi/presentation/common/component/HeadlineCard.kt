package com.alkursi.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alkursi.core.NO_OP
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Source
import java.time.OffsetDateTime

@Composable
internal fun HeadlineCard(article: Article, navigateToArticleDetails: (Article) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigateToArticleDetails(article) })
            .background(color = AppGlobalTheme.colorScheme.background),
    ) {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .heightIn(max = ImageHeight)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
        Spacer(modifier = Modifier.height(AppGlobalTheme.spacing.smallest))
        SourceAndPublishDate(
            sourceName = article.source.name,
            publishDate = article.publishedAtDateTime,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
        Spacer(modifier = Modifier.height(AppGlobalTheme.spacing.smallest))
        SectionTitle(
            title = article.title,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
    }
}

private val ImageHeight = 300.dp

@Composable
@PreviewLightDark
private fun BreakingNewsCardPreview() {
    MaterialTheme {
        Surface {
            HeadlineCard(
                article = Article(
                    title = "Breaking News Title",
                    urlToImage = "https://example.com/image.jpg",
                    source = Source(id = "123", name = "Example Source"),
                    publishedAt = OffsetDateTime.now().toString(),
                    description = "This is a brief description of the breaking news article.",
                    url = "https://example.com/article",
                    author = "John Doe",
                    content = "Full content of the breaking news article goes here."
                ),
                navigateToArticleDetails = { NO_OP }
            )
        }
    }
}