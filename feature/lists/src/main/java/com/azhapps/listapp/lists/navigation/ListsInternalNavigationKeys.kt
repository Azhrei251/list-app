package com.azhapps.listapp.lists.navigation

import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.ListItem
import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModifyList(
    val informativeList: InformativeList,
    val isCreate: Boolean,
    val canDelete: Boolean = true,
) : NavigationKey.WithResult<Pair<Boolean, InformativeList>>

@Parcelize
data class ViewList(
    val informativeList: InformativeList
): NavigationKey

@Parcelize
data class ModifyItem(
    val listItem: ListItem,
    val isCreate: Boolean,
    val canDelete: Boolean = false,
) : NavigationKey.WithResult<Pair<Boolean, ListItem>>

@Parcelize
data class SelectCategory(
    val current: Category?,
): NavigationKey.WithResult<Category>

@Parcelize
data class SelectGroup(
    val current: Group?,
): NavigationKey.WithResult<Group>
