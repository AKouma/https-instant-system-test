package com.alkursi.presentation.feature.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.alkursi.core.NO_OP
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Source
import com.alkursi.presentation.common.component.SourceAndPublishDate
import java.time.OffsetDateTime

@Composable
internal fun NewsCard(article: Article, navigateToArticleDetails: (Article) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { navigateToArticleDetails(article) })
            .padding(AppGlobalTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppGlobalTheme.spacing.smallest)
    ) {
        News(article, Modifier.weight(1f))
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .width(ImageWidth)
                .border(
                    width = 1.dp,
                    color = AppGlobalTheme.colorScheme.outline,
                    shape = RoundedCornerShape(AppGlobalTheme.spacing.thin)
                )
                .clip(RoundedCornerShape(AppGlobalTheme.spacing.thin)),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
private fun News(
    article: Article,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(AppGlobalTheme.spacing.thin)
    ) {
        SourceAndPublishDate(
            sourceName = article.source.name,
            publishDate = article.publishedAtDateTime,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
        Text(
            text = article.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = AppGlobalTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppGlobalTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
        Text(
            text = article.description.orEmpty(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = AppGlobalTheme.typography.bodyMedium,
            color = AppGlobalTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
    }
}

private val ImageWidth = 100.dp

@Composable
@PreviewLightDark
private fun NewsCardPreview() {
    val article = Article(
        source = Source(id = "1", name = "Source 1"),
        title = "Sample News Title",
        description = "This is a sample description for the news article.",
        urlToImage = "https://via.placeholder.com/150",
        publishedAt = OffsetDateTime.now().toString(),
        author = "Author Name",
        content = "This is the content of the news article.",
        url = "https://example.com/sample-news"
    )

    MaterialTheme { Surface { NewsCard(article, navigateToArticleDetails = { NO_OP }) } }
}