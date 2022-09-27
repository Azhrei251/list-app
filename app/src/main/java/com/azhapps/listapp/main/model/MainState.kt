package com.azhapps.listapp.main.model

import com.azhapps.listapp.common.UiState

data class MainState(
    val uiState: UiState = UiState.Loading,
    val done: Boolean = false
)