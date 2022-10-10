package com.azhapps.listapp.groups

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.groups.model.GroupsAction
import com.azhapps.listapp.groups.model.GroupsState

class GroupsViewModel: BaseViewModel<GroupsState, GroupsAction>() {
    override fun dispatch(action: GroupsAction) {
        when (action) {
            is GroupsAction.CreateGroup -> TODO()
            is GroupsAction.DeleteGroup -> TODO()
            is GroupsAction.EditGroup -> TODO()
        }
    }

    override fun initialState(): GroupsState {
        TODO("Not yet implemented")
    }
}