package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.ListItem
import com.google.gson.annotations.SerializedName

data class ModifyListItemRequest(
    val id: Int,
    @SerializedName("list_id") val listId: Int,
    @SerializedName("item_text") val itemText: String,
    val category: Category?,
    val completed: Boolean,
)

fun ListItem.toModifyRequest(listId: Int) = ModifyListItemRequest(
    id = id,
    listId = listId,
    itemText = itemText,
    category = category,
    completed = completed,
)