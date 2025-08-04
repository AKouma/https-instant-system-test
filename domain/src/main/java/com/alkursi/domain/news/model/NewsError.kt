package com.alkursi.domain.news.model

sealed class NewsError : Exception() {

    data object ServiceUnavailable : NewsError()
    data object DataUnavailable : NewsError()
    data object TechnicalError : NewsError()
}