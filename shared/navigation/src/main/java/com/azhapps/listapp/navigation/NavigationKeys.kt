package com.azhapps.listapp.navigation

import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
object Main: NavigationKey

@Parcelize
data class Auth(
    val authOption: AuthOption,
): NavigationKey.WithResult<Boolean> {

    enum class AuthOption {
        LOGIN, REGISTRATION
    }
}

@Parcelize
object Lists: NavigationKey.WithResult<Unit> //This shouldn't need a result but Enro is being weird

@Parcelize
object ListDetailView: NavigationKey