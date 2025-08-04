package com.alkursi.presentation

import com.alkursi.presentation.feature.details.NewsDetailsViewModel
import com.alkursi.presentation.feature.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {

    viewModelOf(::HomeViewModel)
    viewModelOf(::NewsDetailsViewModel)
}