package com.azhapps.listapp.lists.modify.model

sealed class ModifyAction {
    data class CreateCategory(
        val name: String,
    ) : ModifyAction()

    data class UpdateCategory(
        val newCategoryName: String,
    ) : ModifyAction()

    data class UpdateListName(
        val newListName: String,
    ) : ModifyAction()

    data class UpdateGroup(
        val newGroupName: String,
    ) : ModifyAction()

    data class Finalize(
        val deleted: Boolean = false
    ): ModifyAction()
}
