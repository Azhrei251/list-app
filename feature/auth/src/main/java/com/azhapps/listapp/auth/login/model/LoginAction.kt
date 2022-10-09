package com.azhapps.listapp.auth.login.model

sealed class LoginAction {
    data class GetAuthToken(
        val username: String,
        val password: String,
    ): LoginAction()

    object NavigateToRegistration: LoginAction()
}