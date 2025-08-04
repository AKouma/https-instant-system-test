package com.alkursi.presentation.feature.details.component

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alkursi.core.NO_OP
import com.alkursi.design.generic.component.ClickableLinkText
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Source
import com.alkursi.presentation.R
import com.alkursi.presentation.common.component.HeadlineCard
import com.alkursi.presentation.common.openCustomTab
import java.time.OffsetDateTime

@Composable
internal fun NewsDetailsContent(
    modifier: Modifier = Modifier,
    article: Article,
    context: Context
) {
    Column(modifier = modifier) {
        HeadlineCard(article = article, navigateToArticleDetails = { NO_OP })
        Spacer(modifier = Modifier.size(AppGlobalTheme.spacing.small))
        Text(
            text = article.description.orEmpty(),
            style = AppGlobalTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
        Spacer(modifier = Modifier.size(AppGlobalTheme.spacing.small))
        ClickableLinkText(
            text = stringResource(R.string.more_information),
            linkText = article.url,
            linkUrl = article.url,
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small),
            textStyle = AppGlobalTheme.typography.bodyMedium,
            onClick = { url ->
                openCustomTab(context = context, url = url)
            }
        )
        Spacer(modifier = Modifier.size(AppGlobalTheme.spacing.small))
        Text(
            text = stringResource(R.string.written_by, article.author.orEmpty()),
            style = AppGlobalTheme.typography.bodyMedium,
            modifier = Modifier
                .clickable(onClick = { NO_OP })
                .padding(horizontal = AppGlobalTheme.spacing.small)
        )
    }
}

@Composable
@PreviewLightDark
private fun NewsDetailsContentPreview() {

    val context = LocalContext.current

    MaterialTheme {
        Surface {
            NewsDetailsContent(
                article = Article(
                    title = "Sample Article Title",
                    description = "This is a sample description of the article.",
                    url = "https://example.com",
                    author = "John Doe",
                    source = Source(
                        id = "source_id",
                        name = "Source Name"
                    ),
                    urlToImage = "https://example.com/image.jpg",
                    publishedAt = OffsetDateTime.now().toString(),
                    content = null
                ),
                context = context
            )
        }
    }
}