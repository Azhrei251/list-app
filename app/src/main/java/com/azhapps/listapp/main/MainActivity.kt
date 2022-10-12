package com.azhapps.listapp.main

import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.main.navigation.Landing
import com.azhapps.listapp.main.navigation.Splash
import com.azhapps.listapp.navigation.Lists
import com.azhapps.listapp.navigation.Main
import com.azhapps.listapp.navigation.More
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationInstruction
import dev.enro.core.NavigationKey

@AndroidEntryPoint
@NavigationDestination(Main::class)
class MainActivity : BaseActivity() {
    override val initialState = listOf(NavigationInstruction.Forward(Splash))
    override val navigationKeyFilter: (NavigationKey) -> Boolean
        get() = {
            when (it) {
                is Splash, is Landing, is Main, is Lists, is More -> true
                else -> false
            }
        }
}
