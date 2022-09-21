package com.azhapps.listapp.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import dev.enro.core.NavigationKey
import dev.enro.core.TypedNavigationHandle
import dev.enro.core.close

abstract class BaseActivity<T: NavigationKey>: ComponentActivity() {

    open var uiState = UiState.Content
    abstract val navigationHandle: TypedNavigationHandle<T>
    abstract val title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListAppTheme {
                Scaffold(topBar = {
                    TopBar(
                        title = title,
                        backAction = {
                            navigationHandle.close()
                        }
                    )
                }, content = {
                    Column(modifier = Modifier.padding(it)) {
                        BaseUiState(uiState)
                    }
                })
            }
        }
    }

    @Composable
    fun BaseUiState(uiState: UiState) {
        when (uiState) {
            UiState.Content -> Content()
            UiState.Loading -> Loading()
            is UiState.Error -> Error(errorMessage = uiState.cause.message ?: stringResource(R.string.error_default_message))
        }
    }

    @Composable
    abstract fun Content()

    @Composable
    open fun Loading(
        loadingMessage: String? = null,
    ) {
        LoadingPage(loadingMessage)
    }

    @Composable
    open fun Error(
        errorMessage: String,
        errorTitle: String = stringResource(R.string.error_default_title),
    ) {
        ErrorPage(
            errorMessage = errorMessage,
            errorTitle = errorTitle,
            retryAction = {

            },
            cancelAction = {
                navigationHandle.close()
            }
        )
    }
}
