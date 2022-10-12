package com.azhapps.listapp.more.model

import com.azhapps.listapp.common.model.Group

sealed interface SettingsAction {
    object GetAvailableGroups : SettingsAction

    data class UpdateDefaultGroup(
        val group: Group
    ) : SettingsAction
}