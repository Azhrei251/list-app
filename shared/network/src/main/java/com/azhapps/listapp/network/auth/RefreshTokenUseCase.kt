package com.azhapps.listapp.network.auth

import com.azhapps.listapp.network.Api.callApi
import com.azhapps.listapp.network.data.AuthRemoteDataSource
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) {
    suspend operator fun invoke(refreshToken: String) = callApi {
        authRemoteDataSource.refreshAuthToken(refreshToken)
    }
}