package com.alkursi.presentation.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.presentation.R
import com.alkursi.presentation.common.component.SectionTitle
import com.valentinilk.shimmer.shimmer

@Composable
internal fun HomeContentLoading(modifier: Modifier = Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        SectionTitle(
            title = stringResource(R.string.breaking_news),
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
        Spacer(modifier = Modifier.height(AppGlobalTheme.spacing.small))
        Box(
            modifier = Modifier
                .height(ImageHeight)
                .fillMaxWidth()
                .shimmer()
                .background(color = AppGlobalTheme.colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.height(AppGlobalTheme.spacing.normal))
        SectionTitle(
            title = stringResource(R.string.news_brief),
            modifier = Modifier.padding(horizontal = AppGlobalTheme.spacing.small)
        )
        repeat(5) {
            Spacer(modifier = Modifier.height(AppGlobalTheme.spacing.small))
            NewsItem()
        }
    }
}

@Composable
private fun NewsItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppGlobalTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppGlobalTheme.spacing.smallest)
    ) {
        Box(
            Modifier
                .height(ImageWidth)
                .weight(1f)
                .shimmer()
                .background(color = AppGlobalTheme.colorScheme.onBackground)
        )
        Box(
            modifier = Modifier
                .size(ImageWidth)
                .shimmer()
                .background(color = AppGlobalTheme.colorScheme.onBackground)
        )
    }
}

private val ImageHeight = 300.dp
private val ImageWidth = 100.dp

@Composable
@PreviewLightDark
private fun HomeContentLoadingPreview() {
    MaterialTheme {
        Surface { HomeContentLoading() }
    }
}