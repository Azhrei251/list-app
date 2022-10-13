package com.azhapps.listapp.network.auth

import com.azhapps.listapp.network.model.Environment
import javax.inject.Inject

private const val EXPIRY_TIME_OFFSET = 1000L

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager,
) : BaseAuthInterceptor() {

    override fun getAuthHeaderValue(): String {
        var token = tokenManager.getAuthToken()

        return if (token.isNullOrBlank()) {
            getBasicAuthValue(Environment.currentlySelected)
        } else {
            val expiryTime = tokenManager.getExpiryTime()

            if (isTokenExpired(expiryTime, System.currentTimeMillis())) {
                token = RefreshManager.refreshAuthToken(false, tokenManager)
            }
            "Bearer $token"
        }
    }

    private fun isTokenExpired(
        tokenExpiryTime: Long,
        currentTime: Long,
        offset: Long = EXPIRY_TIME_OFFSET
    ): Boolean = tokenExpiryTime < (currentTime - offset)
}