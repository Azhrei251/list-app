package com.azhapps.listapp.more.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.Group

data class SettingsState(
    val defaultGroup: Group? = null,
    val availableGroups: List<Group> = emptyList(),
    val uiState: UiState = UiState.Loading,
)