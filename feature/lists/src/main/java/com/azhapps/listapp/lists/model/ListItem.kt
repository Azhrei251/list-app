package com.azhapps.listapp.lists.model

import com.google.gson.annotations.SerializedName

data class ListItem(
    val id: Int,
    @SerializedName("item_text") var itemText: String,
    var completed: Boolean,
    val category: Category
)
