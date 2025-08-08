package com.alkursi.domain.country

import com.alkursi.domain.country.model.CountryInfo

interface CountryRepository {

    fun setCountryInfo(countryInfo: CountryInfo)
    fun getCountryInfo(): CountryInfo
}