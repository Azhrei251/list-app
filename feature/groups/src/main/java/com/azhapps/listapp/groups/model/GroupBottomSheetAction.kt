package com.azhapps.listapp.groups.model

sealed interface GroupBottomSheetAction {
    data class UpdateName(
        val newName: String,
    ): GroupBottomSheetAction

    data class AddMember(
        val userId: Int,
    ) : GroupBottomSheetAction

    data class RemoveMember(
        val userId: Int,
    ) : GroupBottomSheetAction
}