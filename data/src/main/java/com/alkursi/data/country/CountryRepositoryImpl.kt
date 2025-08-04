package com.alkursi.data.country

import com.alkursi.domain.country.CountryRepository
import com.alkursi.domain.country.model.CountryInfo

class CountryRepositoryImpl : CountryRepository {

    private var countryInfo: CountryInfo = CountryInfo()

    override fun setCountryInfo(countryInfo: CountryInfo) {
        this.countryInfo = countryInfo
    }

    override fun getCountryInfo(): CountryInfo = countryInfo
}