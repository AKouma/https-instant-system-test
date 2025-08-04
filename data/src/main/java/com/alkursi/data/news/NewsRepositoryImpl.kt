package com.alkursi.data.news

import android.os.Build
import androidx.annotation.RequiresApi
import com.alkursi.data.news.model.HeadlinesDto
import com.alkursi.data.news.model.Status
import com.alkursi.domain.news.NewsRepository
import com.alkursi.domain.news.model.Article
import com.alkursi.domain.news.model.Headlines
import com.alkursi.domain.news.model.NewsError
import com.alkursi.domain.news.store.ArticleStore
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsMapper: NewsMapper,
    private val articleStore: ArticleStore
) : NewsRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): Result<Headlines> {
        return try {
            val response =
                newsApi.getTopHeadlines(country = country, page = page, pageSize = pageSize)

            handleResponse(response)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun getArticleById(id: Int): Article? = articleStore.getArticleById(id)

    private fun handleResponse(response: Response<HeadlinesDto>): Result<Headlines> {
        return if (response.isSuccessful) {
            response.body()?.let { headlinesDto ->
                mapResponseStatus(headlinesDto)
            } ?: Result.failure(NewsError.DataUnavailable)
        } else {
            mapHttpError(response.code())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun mapResponseStatus(headlinesDto: HeadlinesDto): Result<Headlines> {
        return when (headlinesDto.status) {
            Status.OK -> Result.success(newsMapper.mapToDomain(headlinesDto))
            Status.MESSAGE -> Result.failure(NewsError.ServiceUnavailable) //todo well handle here articles, by returning articles on store
            Status.ERROR -> Result.failure(NewsError.ServiceUnavailable)
        }
    }

    private fun mapHttpError(code: Int): Result<Headlines> {
        return when (code) {
            401, 403 -> Result.failure(NewsError.TechnicalError)
            in 500..599 -> Result.failure(NewsError.ServiceUnavailable)
            else -> Result.failure(NewsError.ServiceUnavailable)
        }
    }

    private fun handleException(e: Exception): Result<Headlines> {
        return when (e) {
            is HttpException -> Result.failure(NewsError.ServiceUnavailable)
            is JsonSyntaxException, is JsonParseException -> Result.failure(NewsError.TechnicalError)
            else -> Result.failure(NewsError.ServiceUnavailable)
        }
    }

}