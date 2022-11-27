package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.ListItem

sealed interface ViewListAction {

    object RefreshList : ViewListAction

    data class CreateItem(
        val category: Category?
    ) : ViewListAction

    data class ModifyItem(
        val item: ListItem
    ) : ViewListAction

    data class ToggleComplete(
        val item: ListItem
    ) : ViewListAction

    data class ToggleCollapsed(
        val category: Category?
    ) : ViewListAction
}
