package com.azhapps.listapp.auth.registration

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.auth.navigation.Login
import com.azhapps.listapp.auth.navigation.Register
import com.azhapps.listapp.auth.registration.model.RegistrationAction
import com.azhapps.listapp.auth.registration.model.RegistrationState
import com.azhapps.listapp.auth.uc.GetAuthTokenUseCase
import com.azhapps.listapp.auth.uc.RegisterAccountUseCase
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.navigation.Landing
import com.azhapps.listapp.network.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.core.replace
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val getAuthTokenUseCase: GetAuthTokenUseCase,
    private val registerAccountUseCase: RegisterAccountUseCase,
    private val tokenManager: TokenManager,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel<RegistrationState, RegistrationAction>() {

    private val navigationHandle by navigationHandle<Register>()

    override fun initialState() = RegistrationState(
        uiState = UiState.Content,
    )

    override fun dispatch(action: RegistrationAction) {
        when (action) {
            RegistrationAction.NavigateToLogin -> navigationHandle.forward(Login)

            is RegistrationAction.RegisterAccount -> registerAccount(
                action.username,
                action.password,
                action.email
            )
            RegistrationAction.DismissErrorPopup -> updateState { copy(uiState = UiState.Content) }
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

    //TODO Copy paste method is bad need to refactor
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
                navigationHandle.replace(Landing)

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
