package com.alkursi.core

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module{
    single { NetworkConnectivityManager(androidContext()) }
}