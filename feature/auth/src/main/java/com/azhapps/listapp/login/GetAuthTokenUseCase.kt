package com.azhapps.listapp.login

import com.azhapps.listapp.login.data.LoginRemoteDataSource
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class GetAuthTokenUseCase @Inject constructor(
    private val dataSource: LoginRemoteDataSource
) {
    suspend operator fun invoke(
        username: String,
        password: String,
    ) = callApi {
        dataSource.getAuthToken(username, password)
    }
}