package com.alkursi.injector

import com.alkursi.config.configModule
import com.alkursi.core.coreModule
import com.alkursi.data.dataModule
import com.alkursi.design.designModule
import com.alkursi.domain.domainModule
import com.alkursi.presentation.presentationModule

val injectorModule = listOf(
    coreModule,
    dataModule,
    designModule,
    domainModule,
    presentationModule,
    configModule
)