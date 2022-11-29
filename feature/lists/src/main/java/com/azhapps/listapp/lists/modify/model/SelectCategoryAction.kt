package com.azhapps.listapp.lists.modify.model

import com.azhapps.listapp.lists.model.Category

sealed interface SelectCategoryAction {

    object LoadCategories: SelectCategoryAction

    data class Select(
        val category: Category
    ) : SelectCategoryAction

    data class Filter(
        val filter: String
    ) : SelectCategoryAction

    data class Create(
        val name: String,
    ): SelectCategoryAction
}