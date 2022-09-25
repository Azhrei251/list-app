package com.azhapps.listapp.login

import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.login.model.LoginScreenState
import com.azhapps.listapp.login.ui.LoginScreen
import com.azhapps.listapp.login.ui.RegistrationScreen
import com.azhapps.listapp.navigation.Login
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.navigationHandle
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(Login::class)
class LoginActivity : BaseActivity() {
    private val viewModel by enroViewModels<LoginViewModel>()
    private val navigationHandle by navigationHandle<Login>()

    override var uiState: UiState = UiState.Content
    override fun getToolbarTitle() = getString(R.string.login_title)
    override fun backAction(): () -> Unit = navigationHandle::close

    @Composable
    override fun Content() {
        val state = viewModel.collectAsState().value
        uiState = state.uiState

        when (state.loginScreenState) {
            LoginScreenState.LOGIN -> LoginScreen(actor = viewModel::dispatch)
            LoginScreenState.REGISTRATION -> RegistrationScreen(actor = viewModel::dispatch)
        }
    }
}