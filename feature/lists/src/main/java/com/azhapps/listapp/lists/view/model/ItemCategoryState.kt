package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.lists.model.Category

data class ItemCategoryState(
    val category: Category?,
    val items: List<ListItemState>,
    val collapsed: Boolean = false,
)