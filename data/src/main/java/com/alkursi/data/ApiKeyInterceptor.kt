package com.alkursi.data

import com.alkursi.config.Config
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApiKeyInterceptor : Interceptor, KoinComponent {

    private val config by inject<Config>()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("apiKey", config.newsApiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .addHeader("Content-Type", "application/json")
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}