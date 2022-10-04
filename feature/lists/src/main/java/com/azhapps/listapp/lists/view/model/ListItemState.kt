package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.ListItem

data class ListItemState(
    val item: ListItem,
    val uiState: UiState = UiState.Content,
)