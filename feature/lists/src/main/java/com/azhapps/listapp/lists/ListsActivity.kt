package com.azhapps.listapp.lists

import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.lists.navigation.ListSelection
import com.azhapps.listapp.navigation.Lists
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationInstruction

@AndroidEntryPoint
@NavigationDestination(Lists::class)
class ListsActivity : BaseActivity() {
    override val initialState = listOf(NavigationInstruction.Forward(ListSelection))
}