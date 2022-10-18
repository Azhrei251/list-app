package com.azhapps.listapp.groups.model

import com.azhapps.listapp.common.model.Group
import com.google.gson.annotations.SerializedName

data class ModifyGroupRequest(
    val name: String,
    @SerializedName("user_set") val users: List<Int>,
)

fun Group.toModifyGroupRequest() = ModifyGroupRequest(
    name = name,
    users = users.map { it.id }
)
