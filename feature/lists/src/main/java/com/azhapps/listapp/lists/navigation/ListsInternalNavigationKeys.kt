package com.azhapps.listapp.lists.navigation

import com.azhapps.listapp.lists.model.InformativeList
import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
object ListSelection : NavigationKey

@Parcelize
data class EditList(
    val informativeList: InformativeList
) : NavigationKey.WithResult<InformativeList>
