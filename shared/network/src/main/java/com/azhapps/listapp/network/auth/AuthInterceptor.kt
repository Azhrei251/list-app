package com.azhapps.listapp.network.auth

import android.os.Build
import android.util.Base64
import com.azhapps.listapp.network.BuildConfig
import com.azhapps.listapp.network.model.Environment
import okhttp3.Interceptor

private const val EXPIRY_TIME_OFFSET = 1000L

class AuthInterceptor(val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", getAuthHeader())
            .addHeader("User-Agent", getUserAgentString())
            .build()
    )

    private fun getAuthHeader(): String {
        var token = tokenManager.getAuthToken()
        return if (token.isNullOrBlank()) {
            "Basic ${getBasicAuthValue(Environment.currentlySelected)}"
        } else {
            val expiryTime = tokenManager.getExpiryTime()

            if (isTokenExpired(expiryTime, System.currentTimeMillis())) {
                //TODO do refresh
            }
            "Bearer $token"
        }
    }

    private fun getBasicAuthValue(
        environment: Environment
    ): String = Base64.encodeToString("${environment.clientId}:${environment.clientSecret}".toByteArray(), Base64.NO_WRAP)

    private fun isTokenExpired(
        tokenExpiryTime: Long,
        currentTime: Long,
        offset: Long = EXPIRY_TIME_OFFSET
    ): Boolean = tokenExpiryTime < (currentTime - offset)

    private fun getUserAgentString(): String {
        val flavor = if (BuildConfig.DEBUG) "Debug" else "Release"
        return "List App $flavor ${BuildConfig.VERSION_NAME}/Android ${Build.VERSION.RELEASE}"
    }
}