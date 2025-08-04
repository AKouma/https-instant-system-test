package com.alkursi.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun EventListener(event: Flow<UIEvent>, onEvent: suspend (UIEvent) -> Unit) {
    LaunchedEffect(event) {
        event.collect { uiEvent ->
            onEvent(uiEvent)
        }
    }
}

