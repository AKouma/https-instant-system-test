package com.alkursi.presentation.feature.network.model

sealed class NetworkState {
    data object Unknown : NetworkState()
    data object Connected : NetworkState()
    data object Disconnected : NetworkState()
}