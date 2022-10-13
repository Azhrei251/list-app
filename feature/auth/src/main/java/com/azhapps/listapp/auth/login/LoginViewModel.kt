package com.azhapps.listapp.auth.login

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.auth.login.model.LoginAction
import com.azhapps.listapp.auth.login.model.LoginState
import com.azhapps.listapp.auth.navigation.Login
import com.azhapps.listapp.auth.navigation.Register
import com.azhapps.listapp.auth.uc.GetAuthTokenUseCase
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.navigation.Main
import com.azhapps.listapp.network.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.core.replace
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val tokenManager: TokenManager,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel<LoginState, LoginAction>() {

    private val navigationHandle by navigationHandle<Login>()

    override fun initialState() = LoginState(
        uiState = UiState.Content,
    )

    override fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.GetAuthToken -> getAuthToken(
                action.username,
                action.password,
            )

            LoginAction.NavigateToRegistration -> navigationHandle.forward(Register)
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
                navigationHandle.replace(Main)

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
