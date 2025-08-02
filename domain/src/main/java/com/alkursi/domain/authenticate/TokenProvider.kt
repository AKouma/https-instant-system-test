package com.alkursi.domain.authenticate

interface TokenProvider {

    fun getToken(): String?
    fun setToken(token: String?)
    fun clearToken()
    fun isTokenValid(): Boolean
    fun getRefreshToken(): String?
    fun setRefreshToken(token: String?)
}