package com.azhapps.listapp.main

import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.main.navigation.Welcome
import com.azhapps.listapp.navigation.Main
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationInstruction

@AndroidEntryPoint
@NavigationDestination(Main::class)
class MainActivity : BaseActivity() {
    override val initialState = listOf(NavigationInstruction.Forward(Welcome))
}
