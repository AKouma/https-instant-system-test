package com.alkursi.presentation.feature.network

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.alkursi.design.theme.AppGlobalTheme
import com.alkursi.design.theme.extraColors
import com.alkursi.presentation.R
import com.alkursi.presentation.feature.network.model.NetworkState

@Composable
fun NetworkSnackBar(
    networkState: NetworkState
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val connectionUp = stringResource(R.string.network_up)
    val connectionDown = stringResource(R.string.network_down)


    LaunchedEffect(networkState) {
        when (networkState) {
            NetworkState.Connected -> snackbarHostState.showSnackbar(
                message = connectionUp,
                duration = SnackbarDuration.Short
            )

            NetworkState.Disconnected -> snackbarHostState.showSnackbar(
                message = connectionDown,
                duration = SnackbarDuration.Short
            )

            else -> null to null
        }
    }


    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.fillMaxWidth(),
        snackbar = { data ->
            Snackbar(
                containerColor = when (networkState) {
                    NetworkState.Connected -> extraColors.green
                    NetworkState.Disconnected -> AppGlobalTheme.colorScheme.error
                    else -> AppGlobalTheme.colorScheme.surface
                },
                contentColor = AppGlobalTheme.colorScheme.onBackground,
                snackbarData = data
            )
        }
    )
}
