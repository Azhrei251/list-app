package com.azhapps.listapp.login.model

import com.azhapps.listapp.common.UiState

data class LoginState(
    val uiState: UiState,
    val loginScreenState: LoginScreenState,
)

enum class LoginScreenState {
    LOGIN, REGISTRATION
}