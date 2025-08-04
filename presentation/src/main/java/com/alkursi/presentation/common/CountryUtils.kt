package com.alkursi.presentation.common

import com.alkursi.domain.country.model.CountryInfo
import java.util.Locale

object CountryUtils {
    fun getCurrentCountryCode(): String {
        return Locale.getDefault().country.lowercase()
    }

    fun getAllCountryCodes(): List<CountryInfo> {
        return Locale.getAvailableLocales()
            .mapNotNull { locale ->
                val countryCode = locale.country
                val countryName = locale.displayCountry
                if (countryCode.isNotEmpty() && countryName.isNotEmpty()) {
                    CountryInfo(
                        code = countryCode.lowercase(),
                        name = countryName
                    )
                } else null
            }
            .distinctBy { it.code }
            .sortedBy { it.name }
    }
}