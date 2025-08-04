package com.alkursi.domain.news

import com.alkursi.domain.country.CountryRepository
import com.alkursi.domain.news.model.Headlines
import com.alkursi.domain.news.store.ArticleStore

class GetArticlesUseCase(
    private val newsRepository: NewsRepository,
    private val countryRepository: CountryRepository,
    private val articleStore: ArticleStore,
) {

    suspend operator fun invoke(page: Int, pageSize: Int): Headlines =
        newsRepository.getTopHeadlines(country = getCountryCode(), page = page, pageSize = pageSize)
            .onSuccess {
                articleStore.saveArticles(it.articles)
            }.getOrThrow()


    private fun getCountryCode(): String = countryRepository.getCountryInfo().code

}