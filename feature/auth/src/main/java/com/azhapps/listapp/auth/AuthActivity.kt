package com.azhapps.listapp.auth

import com.azhapps.listapp.auth.navigation.Login
import com.azhapps.listapp.auth.navigation.Register
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.navigation.Auth
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationInstruction
import dev.enro.core.navigationHandle

@AndroidEntryPoint
@NavigationDestination(Auth::class)
class AuthActivity : BaseActivity() {
    private val navigationHandle by navigationHandle<Auth>()

    override val initialState: List<NavigationInstruction.Open>
        get() = listOf(
            NavigationInstruction.Forward(
                when (navigationHandle.key.authOption) {
                    Auth.AuthOption.LOGIN -> Login
                    Auth.AuthOption.REGISTRATION -> Register
                }
            )
        )
}