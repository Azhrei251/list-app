package com.azhapps.listapp.login

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.login.model.LoginAction
import com.azhapps.listapp.login.model.LoginScreenState
import com.azhapps.listapp.login.model.LoginState
import com.azhapps.listapp.navigation.Login
import com.azhapps.listapp.network.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.navigationHandle
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val tokenManager: TokenManager,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel<LoginState, LoginAction>() {
    private val navigationHandle by navigationHandle<Login>()

    override fun initialState() = LoginState(
        uiState = UiState.Content,
        loginScreenState = LoginScreenState.LOGIN
    )

    override fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.GetAuthToken -> getAuthToken(
                action.username,
                action.password,
            )

            LoginAction.NavigateToRegistration -> updateState {
                copy(
                    uiState = UiState.Content,
                    loginScreenState = LoginScreenState.REGISTRATION
                )
            }

            LoginAction.NavigateToLogin -> updateState {
                copy(
                    uiState = UiState.Content,
                    loginScreenState = LoginScreenState.LOGIN
                )
            }

            is LoginAction.RegisterAccount -> registerAccount(
                action.username,
                action.password,
                action.email
            )
        }
    }

    private fun getAuthToken(
        username: String,
        password: String,
    ) {
        updateState {
            copy(
                uiState = UiState.Loading
            )
        }
        viewModelScope.launch {
            val result = getAuthTokenUseCase(username, password)
            if (result.success) {
                SelectedAccount.update(username, sharedPreferences)
                tokenManager.setAuthToken(result.data!!)

                updateState {
                    copy(
                        uiState = UiState.Content,
                    )
                }
                navigationHandle.closeWithResult(true)

            } else {
                updateState {
                    copy(
                        uiState = UiState.Error(result.error)
                    )
                }
            }
        }
    }

    private fun registerAccount(
        username: String,
        password: String,
        email: String,
    ) {
        updateState {
            copy(
                uiState = UiState.Loading
            )
        }
        viewModelScope.launch {
            val result = registerAccountUseCase(username, password, email)
            if (result.success) {
                getAuthToken(username, password)

            } else {
                updateState {
                    copy(
                        uiState = UiState.Error(result.error)
                    )
                }
            }
        }
    }
}
