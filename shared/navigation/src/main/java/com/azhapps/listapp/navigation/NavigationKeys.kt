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
object Lists: NavigationKey

@Parcelize
object Groups: NavigationKey

@Parcelize
object More: NavigationKey