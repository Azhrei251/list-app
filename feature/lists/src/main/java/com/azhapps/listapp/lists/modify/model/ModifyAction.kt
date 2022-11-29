package com.azhapps.listapp.lists.modify.model

import com.azhapps.listapp.lists.model.Category

sealed interface ModifyAction {

    object SelectCategory : ModifyAction

    data class UpdateCategory(
        val newCategory: Category?,
    ) : ModifyAction

    data class UpdateListName(
        val newListName: String,
    ) : ModifyAction

    data class UpdateGroup(
        val newGroupName: String,
    ) : ModifyAction

    data class Finalize(
        val deleted: Boolean = false
    ) : ModifyAction
}
