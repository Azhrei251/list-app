package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.common.UiState

data class ViewListState(
    val uiState: UiState = UiState.Loading,
    val itemStates: Map<String, ItemCategoryState>,
    val listTitle: String,
    val listCategory: String,
)
