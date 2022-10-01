package com.azhapps.listapp.lists

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.lists.model.ListsAction
import com.azhapps.listapp.lists.model.ListsState
import com.azhapps.listapp.lists.navigation.ListSelection
import com.azhapps.listapp.navigation.Lists
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor() : BaseViewModel<ListsState, ListsAction>() {
    private val navigationHandle by navigationHandle<Lists>()

    override fun dispatch(action: ListsAction) {
        when (action) {
            ListsAction.NavigateToListSelection -> navigationHandle.forward(ListSelection)
        }
    }

    override fun initialState(): ListsState {
        TODO("Not yet implemented")
    }
}