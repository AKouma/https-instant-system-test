package com.alkursi.data

import com.alkursi.data.authenticate.TokenProviderImpl
import com.alkursi.domain.authenticate.TokenProvider
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.parameter.parametersOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    singleOf(::TokenProviderImpl).bind(TokenProvider::class)

    factory { (withRefresh: Boolean) ->
        Interceptor(
            tokenProvider = get(),
            withRefresh = withRefresh
        )
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor> { parametersOf(false) }) // Default without refresh
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    // single<@@@Api> { get<Retrofit>().create(@@@Api::class.java) } todo
}