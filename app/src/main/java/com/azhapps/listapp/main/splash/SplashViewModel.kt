package com.azhapps.listapp.main.splash

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.main.navigation.Splash
import com.azhapps.listapp.navigation.Landing
import com.azhapps.listapp.navigation.Main
import com.azhapps.listapp.network.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.replace
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val tokenManager: TokenManager,
) : ViewModel() {

    private val navigationHandle by navigationHandle<Splash>()

    fun proceed() {
        SelectedAccount.init(sharedPreferences)

        val navigationKey = if (tokenManager.hasAccount() && tokenManager.getAuthToken() != null) {
            Landing
        } else {
            val defaultAccountName = tokenManager.getDefaultAccountName()
            if (defaultAccountName != null) {
                SelectedAccount.update(defaultAccountName, sharedPreferences)
                if (tokenManager.getAuthToken() == null) {
                    tokenManager.clear(defaultAccountName)
                    Main
                } else {
                    Landing
                }
            } else {
                Main
            }
        }
        navigationHandle.replace(navigationKey)
    }
}