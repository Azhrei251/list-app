package com.azhapps.listapp.lists.edit.model

import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.model.InformativeList

data class EditListState(
    val categoryUiState: UiState = UiState.Loading,
    val groupUiState: UiState = UiState.Loading,
    val availableCategories: List<Category> = emptyList(),
    val availableGroups: List<Group> = emptyList(),
    val currentCategoryName: String = "",
    val currentListName: String = "",
    val currentGroupName: String = "",
    val editable: Boolean = true,
)