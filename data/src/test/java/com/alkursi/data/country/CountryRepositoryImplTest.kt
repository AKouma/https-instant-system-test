package com.alkursi.data.country

import com.alkursi.config.test.AppTest
import com.alkursi.domain.country.model.CountryInfo
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class CountryRepositoryImplTest: AppTest() {

    private val repository = CountryRepositoryImpl()

    @Test
    fun `getCountryInfo should return default country when not set`() = runTest {
        val defaultCountry = CountryInfo()
        val result = repository.getCountryInfo()
        assertEquals(defaultCountry, result)
    }

    @Test
    fun `setCountryInfo should update country info`() = runTest {
        val newCountry = CountryInfo(code = "fr", name = "France")
        repository.setCountryInfo(newCountry)

        val result = repository.getCountryInfo()
        assertEquals(newCountry, result)
    }

    @Test
    fun `setCountryInfo with empty code and name`() = runTest {
        val emptyCountry = CountryInfo(code = "", name = "")
        repository.setCountryInfo(emptyCountry)

        val result = repository.getCountryInfo()
        assertEquals(emptyCountry, result)
    }

    @Test
    fun `setCountryInfo with partial data`() = runTest {
        val partialCountry = CountryInfo(code = "de")
        repository.setCountryInfo(partialCountry)

        val result = repository.getCountryInfo()
        assertEquals(partialCountry, result)
    }
}