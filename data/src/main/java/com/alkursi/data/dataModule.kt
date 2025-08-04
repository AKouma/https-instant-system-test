package com.alkursi.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.alkursi.config.Config
import com.alkursi.data.authenticate.TokenProviderImpl
import com.alkursi.data.country.CountryRepositoryImpl
import com.alkursi.data.news.NewsApi
import com.alkursi.data.news.NewsMapper
import com.alkursi.data.news.NewsRepositoryImpl
import com.alkursi.data.news.store.ArticleStoreImpl
import com.alkursi.domain.authenticate.TokenProvider
import com.alkursi.domain.country.CountryRepository
import com.alkursi.domain.news.NewsRepository
import com.alkursi.domain.news.store.ArticleStore
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<DataStore<Preferences>> {
        androidContext().dataStore
    }

    singleOf(::ArticleStoreImpl).bind(ArticleStore::class)
    singleOf(::TokenProviderImpl).bind(TokenProvider::class)
    singleOf(::CountryRepositoryImpl).bind(CountryRepository::class)

    factoryOf(::ApiKeyInterceptor)

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<ApiKeyInterceptor>())
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Config.newsApiHost)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single<NewsApi> { get<Retrofit>().create(NewsApi::class.java) }

    factoryOf(::NewsMapper)
    factoryOf(::NewsRepositoryImpl).bind(NewsRepository::class)
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")