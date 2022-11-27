package com.azhapps.listapp.lists.selection.model

import com.azhapps.listapp.common.UiState

data class ListSelectionState(
    val uiState: UiState = UiState.Loading,
    val informativeListMap: Map<String, List<ListSelectionItemState>> = emptyMap(),
    val refreshing: Boolean = false,
)
