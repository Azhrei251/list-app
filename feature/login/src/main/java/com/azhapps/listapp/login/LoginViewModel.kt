package com.azhapps.listapp.login

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.login.model.LoginAction
import com.azhapps.listapp.login.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val getAuthTokenUseCase: GetAuthTokenUseCase
): BaseViewModel<LoginState>() {

    fun dispatch(action: LoginAction) {
        when (action) {
            is LoginAction.GetAuthToken -> {
                getAuthToken(
                    action.username,
                    action.password,
                )
            }

            LoginAction.NavigateToSignup -> {
                //TODO
            }
        }
    }

    private fun getAuthToken(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            getAuthTokenUseCase(username, password)
        }
    }
}