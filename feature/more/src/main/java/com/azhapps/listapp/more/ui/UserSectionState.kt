package com.azhapps.listapp.more.ui

import com.azhapps.listapp.common.UiState

data class UserSectionState(
    val email: String = "",
    val username: String = "",
    val uiState: UiState = UiState.Loading,
)
