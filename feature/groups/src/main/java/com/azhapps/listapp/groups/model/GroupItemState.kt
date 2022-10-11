package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.Group

data class GroupItemState(
    val group: Group,
    val uiState: UiState = UiState.Content,
)
