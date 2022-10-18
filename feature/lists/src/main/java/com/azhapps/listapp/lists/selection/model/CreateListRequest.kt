package com.azhapps.listapp.lists.selection.model

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.lists.model.InformativeList
import com.google.gson.annotations.SerializedName

data class CreateListRequest(
    val name: String,
    @SerializedName("group_id") val groupId: Int?,
    val category: Category?,
)

fun InformativeList.toCreateRequest() = CreateListRequest(
    name = name,
    groupId = group?.id,
    category = category,
)