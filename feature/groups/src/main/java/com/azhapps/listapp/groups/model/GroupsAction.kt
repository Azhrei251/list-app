package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.model.Group

sealed interface GroupsAction {
    data class EditGroup(
        val group: Group
    ) : GroupsAction

    data class DeleteGroup(
        val group: Group
    ) : GroupsAction

    data class CreateGroup(
        val group: Group
    ) : GroupsAction
}