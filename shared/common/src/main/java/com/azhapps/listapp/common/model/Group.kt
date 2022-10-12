package com.azhapps.listapp.common.model

import android.os.Parcelable
import com.azhapps.listapp.account.SelectedAccount
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Group(
    val id: Int = -1,
    val name: String,
    val owner: String = "",
    @SerializedName("user_set") val users: List<User> = emptyList()
) : Parcelable

fun Group.isOwnedBySelf() = this.owner == SelectedAccount.currentAccountName
