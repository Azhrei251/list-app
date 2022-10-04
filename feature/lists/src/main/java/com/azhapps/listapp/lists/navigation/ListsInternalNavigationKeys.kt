package com.azhapps.listapp.lists.navigation

import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.ListItem
import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
object ListSelection : NavigationKey

@Parcelize
data class ModifyList(
    val informativeList: InformativeList
) : NavigationKey.WithResult<InformativeList>

@Parcelize
data class ViewList(
    val informativeList: InformativeList
): NavigationKey

@Parcelize
data class ModifyItem(
    val listItem: ListItem
) : NavigationKey.WithResult<ListItem>
