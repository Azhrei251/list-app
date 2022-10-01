package com.azhapps.listapp.lists.selection.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.InformativeList

data class ListSelectionItemState(
    val informativeList: InformativeList,
    val uiState: UiState = UiState.Content,
)
