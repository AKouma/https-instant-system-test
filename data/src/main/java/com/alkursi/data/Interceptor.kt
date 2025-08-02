package com.alkursi.data

import com.alkursi.domain.authenticate.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response
import org.koin.core.component.KoinComponent

class Interceptor(
    private val tokenProvider: TokenProvider,
    private val withRefresh: Boolean = false
): Interceptor, KoinComponent {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        val token = tokenProvider.getToken()
        //possible to check trusted domain logic via a regex per example here
        if (token != null && originalRequest.header("No-Authentication") == null) {
            builder.addHeader("Authorization", "Bearer $token")
        }
        builder.addHeader("Content-Type", "application/json")

        val newRequest = builder.build()
        val response = chain.proceed(newRequest)

        if (withRefresh && response.code == 401) {
           //todo : if having token logic and token is expired, refresh the token here
        }

        return response
    }

}