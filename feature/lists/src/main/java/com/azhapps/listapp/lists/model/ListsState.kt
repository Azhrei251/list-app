package com.azhapps.listapp.lists.model

import com.azhapps.listapp.common.UiState

data class ListsState(
    val uiState: UiState = UiState.Content,
)

