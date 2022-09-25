package com.azhapps.listapp.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage

interface BaseView {

    var uiState: UiState

    @Composable
    fun BaseUiState(uiState: UiState) {
        when (uiState) {
            UiState.Content -> Content()
            UiState.Loading -> Loading()
            is UiState.Error -> Error(errorMessage = uiState.cause?.message ?: stringResource(R.string.error_default_message))
        }
    }

    fun backAction(): () -> Unit

    @Composable
    fun Content()

    @Composable
    fun Loading(
        loadingMessage: String? = null,
    ) {
        LoadingPage(loadingMessage)
    }

    @Composable
    fun Error(
        errorMessage: String,
        errorTitle: String = stringResource(R.string.error_default_title),
    ) {
        ErrorPage(
            errorMessage = errorMessage,
            errorTitle = errorTitle,
            retryAction = {

            },
            cancelAction = {
                backAction()
            }
        )
    }
}