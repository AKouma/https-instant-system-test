package com.alkursi.base_architecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.alkursi.design.theme.BasearchitectureTheme
import com.alkursi.injector.injectorModule
import com.alkursi.presentation.feature.home.HomeScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin()
        setContent {
            BasearchitectureTheme {
                HomeScreen()
            }
        }
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(buildList {
                add(appModule)
                addAll(injectorModule)
            })
        }
    }
}