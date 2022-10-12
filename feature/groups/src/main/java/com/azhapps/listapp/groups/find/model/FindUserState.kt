package com.azhapps.listapp.groups.find.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.User

data class FindUserState(
    val uiState: UiState = UiState.Loading,
    val availableUsers: List<User> = emptyList(),
    val searchFilter: String = "",
)
