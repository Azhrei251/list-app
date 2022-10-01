package com.azhapps.listapp.lists.modify.model

sealed class ModifyListAction {
    data class CreateCategory(
        val name: String,
    ) : ModifyListAction()

    data class UpdateCategory(
        val newCategoryName: String,
    ) : ModifyListAction()

    data class UpdateListName(
        val newListName: String,
    ) : ModifyListAction()

    data class UpdateGroup(
        val newGroupName: String,
    ) : ModifyListAction()

    object Finalize: ModifyListAction()
}
