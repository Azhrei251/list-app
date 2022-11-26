package com.azhapps.listapp.network.auth

import android.accounts.AccountManager
import com.azhapps.listapp.account.ApplicationModule
import com.azhapps.listapp.network.BuildConfig
import com.azhapps.listapp.network.data.AuthRemoteDataSource
import com.azhapps.listapp.network.model.Environment
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RefreshManager {

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(BasicAuthInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .client(httpClient)
        .baseUrl(Environment.currentlySelected.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val refreshTokenUseCase = RefreshTokenUseCase(retrofit.create(AuthRemoteDataSource::class.java))
    private val mutex = Mutex()

    fun refreshAuthToken(
        clearOnFailure: Boolean,
        tokenManager: TokenManager = TokenManager(AccountManager.get(ApplicationModule.applicationContext))
    ): String = runBlocking {
        mutex.withLock {
            doTokenRefresh(clearOnFailure, tokenManager)
        }
    }

    private suspend fun doTokenRefresh(
        clearOnFailure: Boolean,
        tokenManager: TokenManager = TokenManager(AccountManager.get(ApplicationModule.applicationContext))
    ): String {
        if (tokenManager.isTokenExpired()) {
            val refreshToken = tokenManager.getRefreshToken()
            if (refreshToken != null) {
                val refreshResult = refreshTokenUseCase(refreshToken)

                if (refreshResult.success) {
                    tokenManager.setAuthToken(refreshResult.data!!)
                    return refreshResult.data.accessToken
                } else {
                    if (clearOnFailure) {
                        tokenManager.clear()
                    }
                    throw IOException("Failed to refresh auth token", refreshResult.error)
                }
            } else {
                if (clearOnFailure) {
                    tokenManager.clear()
                }
                throw IOException("Refresh token missing")
            }
        } else {
            val accessToken = tokenManager.getAuthToken()
            if (accessToken.isNullOrBlank()) {
                throw IOException("Access token was not expired but blank")
            }
            return accessToken
        }
    }
}