package com.azhapps.listapp.more.model

import com.azhapps.listapp.network.model.Environment

sealed interface DeveloperOptionsAction {
    data class UpdateEnvironment(
        val environment: Environment
    ) : DeveloperOptionsAction
}