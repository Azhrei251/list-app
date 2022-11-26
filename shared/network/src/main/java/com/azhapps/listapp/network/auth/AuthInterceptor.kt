package com.azhapps.listapp.network.auth

import com.azhapps.listapp.network.model.Environment
import javax.inject.Inject


class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : BaseAuthInterceptor() {

    override fun getAuthHeaderValue(): String {
        var token = tokenManager.getAuthToken()

        return if (token.isNullOrBlank()) {
            getBasicAuthValue(Environment.currentlySelected)
        } else {
            if (tokenManager.isTokenExpired()) {
                token = RefreshManager.refreshAuthToken(false, tokenManager)
            }
            "Bearer $token"
        }
    }
}