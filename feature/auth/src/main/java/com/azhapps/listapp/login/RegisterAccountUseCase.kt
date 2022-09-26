package com.azhapps.listapp.login

import com.azhapps.listapp.login.data.LoginRemoteDataSource
import com.azhapps.listapp.network.Api
import javax.inject.Inject

class RegisterAccountUseCase @Inject constructor(
    private val dataSource: LoginRemoteDataSource
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        email: String
    ) = Api.callApi {
        dataSource.registerAccount(username, password, email)
    }
}