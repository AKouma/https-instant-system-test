package com.alkursi.domain.news

import com.alkursi.domain.news.model.Article

class GetArticleByIdUseCase(private val newsRepository: NewsRepository) {

    suspend operator fun invoke(id: Int): Article? = newsRepository.getArticleById(id)
}