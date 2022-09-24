package com.azhapps.listapp.login.model

sealed class LoginAction {
    data class GetAuthToken(
        val username: String,
        val password: String,
    ): LoginAction()

    object NavigateToSignup: LoginAction()
}