package com.azhapps.listapp.lists.selection.model

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.InformativeList

sealed class ListSelectionAction {
    object GetAllLists : ListSelectionAction()

    data class ShowList(
        val informativeList: InformativeList
    ) : ListSelectionAction()

    data class EditList(
        val informativeList: InformativeList
    ) : ListSelectionAction()

    data class CreateList(
        val category: Category?
    ) : ListSelectionAction()
}
