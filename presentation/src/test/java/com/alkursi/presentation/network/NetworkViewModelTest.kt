package com.alkursi.presentation.network

import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import com.alkursi.config.test.AppTest
import com.alkursi.core.NetworkConnectivityManager
import com.alkursi.presentation.feature.network.NetworkViewModel
import com.alkursi.presentation.feature.network.model.NetworkState
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class NetworkViewModelTest : AppTest() {

    @RelaxedMockK
    private lateinit var networkManager: NetworkConnectivityManager

    private lateinit var viewModel: NetworkViewModel

    override fun setup() {
        super.setup()
        viewModel = NetworkViewModel(networkManager)
    }

    @Test
    fun `networkState should update to Connected and Disconnected`() = runTest {
        turbineScope {
            val connectionFlow = MutableStateFlow(false)
            every { networkManager.isConnected } returns connectionFlow

                    viewModel.initNetworkListener()

            val state = viewModel.networkState.testIn(backgroundScope)
            connectionFlow.update { true }

            assertEquals(NetworkState.Unknown, state.awaitItem())
            assertEquals(NetworkState.Connected, state.awaitItem())

            connectionFlow.update { false }
            assertEquals(NetworkState.Disconnected, state.awaitItem())
        }
    }
}
