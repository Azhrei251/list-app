package com.azhapps.listapp.lists.edit.model

sealed class EditListAction {
    data class CreateCategory(
        val name: String,
    ) : EditListAction()

    data class UpdateCategory(
        val newCategoryName: String,
    ) : EditListAction()

    data class UpdateListName(
        val newListName: String,
    ) : EditListAction()

    data class UpdateGroup(
        val newGroupName: String,
    ) : EditListAction()

    object Finalize: EditListAction()
}
