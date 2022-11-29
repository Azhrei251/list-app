package com.azhapps.listapp.lists.modify.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category

data class SelectCategoryState(
    val uiState: UiState = UiState.Loading,
    val current: Category? = null,
    val available: List<Category> = emptyList(),
    val filter: String = "",
)