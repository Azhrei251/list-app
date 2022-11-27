package com.azhapps.listapp.navigation

import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize


@Parcelize
object Initial: NavigationKey

@Parcelize
object Main: NavigationKey

@Parcelize
object Landing: NavigationKey

@Parcelize
data class Auth(
    val authOption: AuthOption,
): NavigationKey.WithResult<Boolean> {

    enum class AuthOption {
        LOGIN, REGISTRATION
    }
}

@Parcelize
object DeveloperOptions: NavigationKey
