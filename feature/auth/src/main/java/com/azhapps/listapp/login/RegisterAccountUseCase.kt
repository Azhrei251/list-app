package com.azhapps.listapp.login

import com.azhapps.listapp.login.data.LoginRemoteDataSource
import com.azhapps.listapp.network.Api
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val dataSource: LoginRemoteDataSource
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        email: String
    ) = callApi {
        dataSource.registerAccount(username, password, email)
    }
}