package com.azhapps.listapp.lists.selection.model

import com.azhapps.listapp.lists.model.InformativeList
import com.google.gson.annotations.SerializedName

data class CreateListRequest(
    val name: String,
    @SerializedName("group_id") val groupId: Int?,
    @SerializedName("category_id") val categoryId: Int?,
)

fun InformativeList.toCreateRequest() = CreateListRequest(
    name = name,
    groupId = group?.id,
    categoryId = category?.id,
)