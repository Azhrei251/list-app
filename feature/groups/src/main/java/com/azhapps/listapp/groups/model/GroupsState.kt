package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.Group

data class GroupsState(
    val uiState: UiState = UiState.Loading,
    val groupItemStates: List<GroupItemState> = emptyList(),
    val showConfirmDeleteGroupDialog: Boolean = false,
    val selectedGroup: Group? = null,
)
