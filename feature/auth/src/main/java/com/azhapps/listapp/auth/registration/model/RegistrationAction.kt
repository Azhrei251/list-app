package com.azhapps.listapp.auth.registration.model

sealed class RegistrationAction {
    object NavigateToLogin: RegistrationAction()

    data class RegisterAccount(
        val username: String,
        val password: String,
        val email: String,
    ): RegistrationAction()
}