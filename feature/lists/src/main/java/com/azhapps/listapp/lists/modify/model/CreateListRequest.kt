package com.azhapps.listapp.lists.modify.model

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.model.InformativeList

data class CreateListRequest(
    val name: String,
    val group: Group?,
    val category: Category?,
)

fun InformativeList.toCreateRequest() = CreateListRequest(
    name = name,
    group = group,
    category = category,
)