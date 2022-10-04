package com.azhapps.listapp.lists.view

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.selection.UNCATEGORIZED
import com.azhapps.listapp.lists.selection.mapByItemCategory
import com.azhapps.listapp.lists.view.model.ListItemState
import com.azhapps.listapp.lists.view.model.ViewListAction
import com.azhapps.listapp.lists.view.model.ViewListState
import dev.enro.viewmodel.navigationHandle

class ViewListViewModel: BaseViewModel<ViewListState, ViewListAction>() {
    private val navigationHandle by navigationHandle<ViewList>()

    override fun initialState() = navigationHandle.key.informativeList.let { informativeList ->
        ViewListState(
            itemStates = informativeList.items.mapByItemCategory(),
            title = informativeList.name,
            category = informativeList.category?.name ?: UNCATEGORIZED
        )
    }

    override fun dispatch(action: ViewListAction) {
        TODO("Not yet implemented")
    }
}