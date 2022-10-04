package com.azhapps.listapp.lists.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListItem(
    val id: Int = -1,
    @SerializedName("item_text") var itemText: String = "",
    var completed: Boolean = false,
    val category: Category? = null,
): Parcelable
