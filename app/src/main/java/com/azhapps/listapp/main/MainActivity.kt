package com.azhapps.listapp.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.main.navigation.Welcome
import com.azhapps.listapp.navigation.Landing
import com.azhapps.listapp.navigation.Main
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationKey
import dev.enro.core.compose.EmptyBehavior
import dev.enro.core.forward
import dev.enro.core.navigationHandle

@AndroidEntryPoint
@NavigationDestination(Main::class)
class MainActivity : BaseActivity() {

    private val navigationHandle by navigationHandle<Main>()
    override val emptyBehavior = EmptyBehavior.CloseParent

    override val navigationKeyFilter: (NavigationKey) -> Boolean
        get() = {
            when (it) {
                is Welcome, is Landing, is Main -> true
                else -> false
            }
        }

    @Composable
    override fun InitialContent() {
        LaunchedEffect(Unit) {
            navigationHandle.forward(Welcome)
        }
    }
}
