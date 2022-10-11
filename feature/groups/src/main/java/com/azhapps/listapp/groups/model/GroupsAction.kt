package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.model.Group

sealed interface GroupsAction {
    object GetGroups: GroupsAction

    data class EditGroup(
        val group: Group
    ) : GroupsAction

    data class DeleteGroup(
        val group: Group
    ) : GroupsAction

    object CreateGroup : GroupsAction
}