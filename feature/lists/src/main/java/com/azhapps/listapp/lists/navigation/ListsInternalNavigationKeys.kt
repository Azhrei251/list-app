package com.azhapps.listapp.lists.navigation

import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

sealed class ListsInternalNavigationKeys: NavigationKey {

    @Parcelize
    object ListSelection: ListsInternalNavigationKeys()
}