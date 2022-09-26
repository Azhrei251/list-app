package com.azhapps.listapp.main

import android.content.SharedPreferences
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.main.model.MainAction
import com.azhapps.listapp.main.model.MainState
import com.azhapps.listapp.navigation.Auth
import com.azhapps.listapp.navigation.ListSelection
import com.azhapps.listapp.navigation.Main
import com.azhapps.listapp.network.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.core.result.registerForNavigationResult
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    sharedPreferences: SharedPreferences,
    tokenManager: TokenManager,
) : BaseViewModel<MainState, MainAction>() {

    private val navigationHandle by registerForNavigationResult<Unit> {
        //TODO Figure out why Enro is dying trying to make a normal nav handle
    }
    private val loginResult by registerForNavigationResult<Boolean> {
        if (it) {
            dispatch(MainAction.NavigateToLists)
        } else {
            updateState {
                copy(uiState = UiState.Error())
            }
        }
    }

    init {
        SelectedAccount.init(sharedPreferences)

        if (tokenManager.hasAccount() && tokenManager.getAuthToken() != null) {
            dispatch(MainAction.NavigateToLists)
        } else {
            val defaultAccountName = tokenManager.getDefaultAccountName()
            if (defaultAccountName != null) {
                SelectedAccount.update(defaultAccountName, sharedPreferences)
                dispatch(MainAction.NavigateToLists)
            }
        }
    }

    override fun dispatch(action: MainAction) {
        when (action) {
            MainAction.NavigateToLists -> navigationHandle.open(ListSelection)

            MainAction.NavigateToLogin -> loginResult.open(Auth(Auth.AuthOption.LOGIN))

            MainAction.NavigateToRegister -> loginResult.open(Auth(Auth.AuthOption.REGISTRATION))
        }
    }

    override fun initialState() = MainState()
}