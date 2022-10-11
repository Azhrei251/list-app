package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.model.User

data class GroupBottomSheetState(
    val currentName: String = "",
    val currentMembers: List<User> = emptyList()
)