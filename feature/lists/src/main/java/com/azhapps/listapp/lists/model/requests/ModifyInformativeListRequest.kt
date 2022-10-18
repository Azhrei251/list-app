package com.azhapps.listapp.lists.model.requests

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.ListItem
import com.google.gson.annotations.SerializedName

data class ModifyInformativeListRequest(
    var name: String,
    val date: String = "",
    var items: List<ListItem> = emptyList(),
    val category: Category?,
    val owner: String = "",
    @SerializedName("group_id") val groupId: Int? = null,
)

fun InformativeList.toModifyRequest() = ModifyInformativeListRequest(
    name = name,
    date = date,
    items = items,
    category = category,
    owner = owner,
    groupId = group?.id
)