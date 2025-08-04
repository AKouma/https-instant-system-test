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

@Composable
internal fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = AppGlobalTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = AppGlobalTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Composable
@PreviewLightDark
private fun SectionTitlePreview() {
    MaterialTheme {
        Surface {
            SectionTitle(
                title = "Section Title",
                modifier = Modifier.padding(AppGlobalTheme.spacing.small)
            )
        }
    }
}