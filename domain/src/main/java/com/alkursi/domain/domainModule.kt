package com.alkursi.domain

import com.alkursi.domain.news.GetArticleByIdUseCase
import com.alkursi.domain.news.GetArticlesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {

    factoryOf(::GetArticlesUseCase)
    factoryOf(::GetArticleByIdUseCase)
}