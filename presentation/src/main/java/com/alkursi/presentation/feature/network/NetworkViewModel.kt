package com.alkursi.presentation.feature.network

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alkursi.core.NetworkConnectivityManager
import com.alkursi.presentation.feature.network.model.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NetworkViewModel(
    private val networkManager: NetworkConnectivityManager
) : ViewModel() {

    private val _networkState = MutableStateFlow<NetworkState>(NetworkState.Unknown)
    val networkState: StateFlow<NetworkState> =
        _networkState.stateIn(viewModelScope, SharingStarted.Lazily, NetworkState.Unknown)

    fun initNetworkListener() {
        viewModelScope.launch {
            networkManager.isConnected.collect { isConnected ->
                _networkState.update {
                    if (isConnected) NetworkState.Connected else NetworkState.Disconnected
                }
            }
        }

        networkManager.startListening()
    }

    override fun onCleared() {
        super.onCleared()
        networkManager.stopListening()
    }
}