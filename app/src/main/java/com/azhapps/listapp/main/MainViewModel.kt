package com.azhapps.listapp.main

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.main.model.MainAction
import com.azhapps.listapp.main.model.MainState
import com.azhapps.listapp.main.navigation.Landing
import com.azhapps.listapp.main.navigation.Welcome
import com.azhapps.listapp.navigation.Auth
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.replace
import dev.enro.core.result.registerForNavigationResult
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainAction>() {

    private val navigationHandle by navigationHandle<Welcome>()
    private val loginResult by registerForNavigationResult<Boolean> {
        if (it) {
            dispatch(MainAction.NavigateToLanding)
        } else {
            updateState {
                copy(uiState = UiState.Error())
            }
        }
    }


    override fun dispatch(action: MainAction) {
        when (action) {
            MainAction.NavigateToLanding -> navigationHandle.replace(Landing)

            MainAction.NavigateToLogin -> loginResult.open(Auth(Auth.AuthOption.LOGIN))

            MainAction.NavigateToRegister -> loginResult.open(Auth(Auth.AuthOption.REGISTRATION))
        }
    }

    override fun initialState() = MainState()
}