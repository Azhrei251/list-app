package com.azhapps.listapp.groups.model

import com.google.gson.annotations.SerializedName

data class CreateGroupRequest(
    val name: String,
    @SerializedName("user_set") val users: List<Int>,
)
