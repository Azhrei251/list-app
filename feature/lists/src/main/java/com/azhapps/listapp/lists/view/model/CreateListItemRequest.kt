package com.azhapps.listapp.lists.view.model

import com.azhapps.listapp.lists.model.ListItem
import com.google.gson.annotations.SerializedName

data class CreateListItemRequest(
    @SerializedName("list_id") val listId: Int,
    @SerializedName("item_text") val itemText: String,
    @SerializedName("category_id") val categoryId: Int? = null,
)

fun ListItem.toCreateRequest(listId: Int) = CreateListItemRequest(
    listId = listId,
    itemText = itemText,
    categoryId = category?.id,
)