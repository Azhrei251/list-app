package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.UiState

data class GroupsState(
    val uiState: UiState = UiState.Loading,
    val groupItemStates: List<GroupItemState> = emptyList()
)
