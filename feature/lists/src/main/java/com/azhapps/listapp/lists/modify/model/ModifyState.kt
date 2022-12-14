package com.azhapps.listapp.lists.modify.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.common.model.Group

data class ModifyState(
    val categoryUiState: UiState = UiState.Loading,
    val groupUiState: UiState = UiState.Loading,
    val availableCategories: List<Category> = emptyList(),
    val availableGroups: List<Group> = emptyList(),
    val currentCategory: Category? = null,
    val currentName: String = "",
    val currentGroupName: String = "",
    val editable: Boolean = true,
    val canDelete: Boolean = true,
    val isCreate: Boolean = false,
)