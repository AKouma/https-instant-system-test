package com.alkursi.domain.news

import com.alkursi.domain.country.CountryRepository
import com.alkursi.domain.news.model.Headlines

class GetArticlesUseCase(
    private val newsRepository: NewsRepository,
    private val countryRepository: CountryRepository,
) {

    suspend operator fun invoke(page: Int, pageSize: Int): Headlines =
        newsRepository.getTopHeadlines(country = getCountryCode(), page = page, pageSize = pageSize)
            .getOrThrow()


    private fun getCountryCode(): String = countryRepository.getCountryInfo().code

}