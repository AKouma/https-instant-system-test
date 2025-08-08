package com.alkursi.config.test

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import io.mockk.MockKAnnotations
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [28],
    manifest = Config.NONE,
    application = Application::class,
    shadows = []
)
abstract class AppTest {

    lateinit var context : Context

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    val testDispatcher = StandardTestDispatcher()

    @Before
    open fun setup(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
         context = ApplicationProvider.getApplicationContext()
    }

    @OptIn(DelicateCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}