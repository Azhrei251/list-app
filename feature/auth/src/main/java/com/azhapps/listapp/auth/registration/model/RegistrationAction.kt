package com.azhapps.listapp.auth.registration.model

sealed interface RegistrationAction {
    object NavigateToLogin: RegistrationAction

    data class RegisterAccount(
        val username: String,
        val password: String,
        val email: String,
    ): RegistrationAction

    object DismissErrorPopup : RegistrationAction
}