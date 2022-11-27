package com.azhapps.listapp.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.azhapps.listapp.auth.navigation.Login
import com.azhapps.listapp.auth.navigation.Register
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.navigation.Auth
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.EmptyBehavior
import dev.enro.core.forward
import dev.enro.core.navigationHandle

@AndroidEntryPoint
@NavigationDestination(Auth::class)
class AuthActivity : BaseActivity() {

    override val emptyBehavior = EmptyBehavior.CloseParent

    private val navigationHandle by navigationHandle<Auth>()

    @Composable
    override fun InitialContent() {
        LaunchedEffect(Unit) {
            navigationHandle.forward(
                when (navigationHandle.key.authOption) {
                    Auth.AuthOption.LOGIN -> Login
                    Auth.AuthOption.REGISTRATION -> Register
                }
            )
        }
    }
}