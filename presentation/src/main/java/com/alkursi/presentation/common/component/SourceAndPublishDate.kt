package com.alkursi.presentation.common.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.presentation.common.formatRelative
import java.time.OffsetDateTime

@Composable
internal fun SourceAndPublishDate(
    sourceName: String,
    publishDate: OffsetDateTime?,
    modifier: Modifier = Modifier
) {
    Text(
        text = getSourceAndPublishTime(sourceName = sourceName, publishDate = publishDate),
        style = AppGlobalTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = AppGlobalTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Composable
private fun getSourceAndPublishTime(sourceName: String, publishDate: OffsetDateTime?): String =
    "$sourceName - ${publishDate?.formatRelative().orEmpty()}"

@Composable
@PreviewLightDark
private fun SourceAndPublishDatePreview() {
    MaterialTheme {
        Surface {
            SourceAndPublishDate(
                sourceName = "Flash News",
                publishDate = OffsetDateTime.now(),
                modifier = Modifier.padding(AppGlobalTheme.spacing.small)
            )
        }
    }
}