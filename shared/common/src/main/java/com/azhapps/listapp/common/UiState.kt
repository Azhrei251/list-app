package com.azhapps.listapp.common

sealed class UiState {
    object Content : UiState()
    object Loading : UiState()
    class Error(val cause: Throwable) : UiState()
}