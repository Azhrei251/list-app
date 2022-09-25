package com.azhapps.listapp.login.model

sealed class LoginAction {
    object NavigateToLogin: LoginAction()

    data class GetAuthToken(
        val username: String,
        val password: String,
    ): LoginAction()

    object NavigateToRegistration: LoginAction()
}