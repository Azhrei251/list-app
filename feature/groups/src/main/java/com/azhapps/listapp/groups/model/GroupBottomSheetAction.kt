package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.model.User

sealed interface GroupBottomSheetAction {
    data class UpdateName(
        val newName: String,
    ): GroupBottomSheetAction

    object FindMember: GroupBottomSheetAction

    data class ConfirmRemoveMember(
        val user: User,
    ) : GroupBottomSheetAction

    data class RemoveMember(
        val user: User,
    ) : GroupBottomSheetAction

    object DismissDialog : GroupBottomSheetAction

    object Finalize: GroupBottomSheetAction
}