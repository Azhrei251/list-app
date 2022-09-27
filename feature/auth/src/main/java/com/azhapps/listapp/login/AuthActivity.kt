package com.azhapps.listapp.login

import android.os.Bundle
import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.login.model.LoginAction
import com.azhapps.listapp.login.model.LoginScreenState
import com.azhapps.listapp.login.ui.LoginScreen
import com.azhapps.listapp.login.ui.RegistrationScreen
import com.azhapps.listapp.navigation.Auth
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.navigationHandle
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(Auth::class)
class AuthActivity : BaseActivity() {
    private val viewModel by enroViewModels<AuthViewModel>()
    private val navigationHandle by navigationHandle<Auth>()

    override var uiState: UiState = UiState.Content
    override fun getToolbarTitle() = getString(R.string.auth_title)
    override fun backAction(): () -> Unit = navigationHandle::close

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (navigationHandle.key.authOption) {
            Auth.AuthOption.LOGIN -> viewModel.dispatch(LoginAction.NavigateToLogin)
            Auth.AuthOption.REGISTRATION -> viewModel.dispatch(LoginAction.NavigateToRegistration)
        }
    }

    @Composable
    override fun Content() {
        val state = viewModel.collectAsState()
        uiState = state.uiState

        when (state.loginScreenState) {
            LoginScreenState.LOGIN -> LoginScreen(actor = viewModel::dispatch)
            LoginScreenState.REGISTRATION -> RegistrationScreen(actor = viewModel::dispatch)
        }
    }
}