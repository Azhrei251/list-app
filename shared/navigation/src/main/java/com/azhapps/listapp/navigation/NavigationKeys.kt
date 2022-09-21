package com.azhapps.listapp.navigation

import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
object Main: NavigationKey

@Parcelize
object Login: NavigationKey.WithResult<Boolean>

@Parcelize
object ListSelection: NavigationKey

@Parcelize
object ListDetailView: NavigationKey