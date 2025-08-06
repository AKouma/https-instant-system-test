package com.alkursi.presentation.feature.network

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.alkursi.core.NetworkConnectivityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun rememberNetworkConnectivity(): Boolean {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(false) }

    DisposableEffect(context) {
        val networkManager = NetworkConnectivityManager(context)

        val scope = CoroutineScope(Dispatchers.Main)
        val job = scope.launch {
            networkManager.isConnected.collect { connected ->
                isConnected = connected
            }
        }

        networkManager.startListening()

        onDispose {
            networkManager.stopListening()
            job.cancel()
        }
    }

    return isConnected
}