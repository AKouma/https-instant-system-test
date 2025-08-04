package com.alkursi.config

import org.koin.dsl.module

val configModule = module {
    single { Config }
}