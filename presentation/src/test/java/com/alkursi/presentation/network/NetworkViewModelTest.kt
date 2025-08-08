package com.alkursi.presentation.network

import app.cash.turbine.test
import com.alkursi.config.test.AppTest
import com.alkursi.core.NetworkConnectivityManager
import com.alkursi.presentation.feature.network.NetworkViewModel
import com.alkursi.presentation.feature.network.model.NetworkState
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `networkState should update to Connected and Disconnected`() = runTest {
        val connectionFlow = MutableSharedFlow<Boolean>(replay = 1)
        every { networkManager.isConnected } returns connectionFlow as StateFlow<Boolean>

        viewModel.initNetworkListener()

        val job = launch {
            viewModel.networkState.test {
                connectionFlow.emit(true)
                assertEquals(NetworkState.Connected, awaitItem())

                connectionFlow.emit(false)
                assertEquals(NetworkState.Disconnected, awaitItem())
            }
        }

        advanceUntilIdle()
        job.cancel()
    }
}
