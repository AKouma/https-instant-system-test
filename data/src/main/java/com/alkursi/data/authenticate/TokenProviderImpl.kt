package com.alkursi.data.authenticate

import com.alkursi.domain.authenticate.TokenProvider

class TokenProviderImpl:  TokenProvider{
    override fun getToken(): String? {
        return null
    }

    override fun setToken(token: String?) {
        TODO("Not yet implemented")
    }

    override fun clearToken() {
        TODO("Not yet implemented")
    }

    override fun isTokenValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRefreshToken(): String? {
        return null
    }

    override fun setRefreshToken(token: String?) {
        TODO("Not yet implemented")
    }
}