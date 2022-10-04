package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.common.UiState

data class ViewListState(
    val uiState: UiState = UiState.Loading,
    val itemStates: Map<String, List<ListItemState>>,
    val title: String,
    val category: String,
)
