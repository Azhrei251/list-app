package com.azhapps.listapp.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import dev.enro.core.NavigationKey
import dev.enro.core.TypedNavigationHandle
import dev.enro.core.close

abstract class BaseActivity<T: NavigationKey>: FragmentActivity() {

    open var uiState = UiState.Content
    abstract val navigationHandle: TypedNavigationHandle<T>
    open val shouldShowBackArrow = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListAppTheme {
                Scaffold(topBar = {
                    TopBar(
                        title = getToolbarTitle(),
                        backAction = {
                            navigationHandle.close()
                        },
                        showBackArrow = shouldShowBackArrow
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

    abstract fun getToolbarTitle(): String

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
