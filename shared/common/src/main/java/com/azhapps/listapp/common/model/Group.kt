package com.azhapps.listapp.common.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: Int,
    val name: String,
    @SerializedName("user_set") val users: List<User> = emptyList()
): Parcelable