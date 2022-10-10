package com.azhapps.listapp.lists.navigation

import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.ListItem
import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
object ListSelection : NavigationKey

@Parcelize
data class ModifyList(
    val informativeList: InformativeList,
    val canDelete: Boolean = true,
) : NavigationKey.WithResult<Pair<Boolean, InformativeList>>

@Parcelize
data class ViewList(
    val informativeList: InformativeList
): NavigationKey

@Parcelize
data class ModifyItem(
    val listItem: ListItem,
    val canDelete: Boolean = false,
) : NavigationKey.WithResult<Pair<Boolean, ListItem>>
