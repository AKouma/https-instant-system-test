package com.alkursi.design.generic.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.alkursi.core.NO_OP
import com.alkursi.design.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FNewsSettingsToolbar(onLongPress: () -> Unit) {

    var isPressed by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.settings),
            contentDescription = stringResource(R.string.country_settings),
            modifier = Modifier
                .size(ImageSize)
                .alpha(getAlpha(isPressed))
                .combinedClickable(
                    onClick = { NO_OP },
                    onLongClick = { onLongPress },
                )
        )
    }
}

@Composable
private fun getAlpha(isPressed: Boolean): Float = if (isPressed) 0.7f else 1f

private val ImageSize = 80.dp

@Composable
@PreviewLightDark
private fun FNewsSettingsToolbarPreview() {
    Surface { FNewsSettingsToolbar(onLongPress = { NO_OP }) }
}