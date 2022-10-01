package com.azhapps.listapp.lists.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: Int,
    val name: String,
): Parcelable