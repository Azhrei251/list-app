package com.azhapps.listapp.groups.navigation

import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.model.User
import dev.enro.core.NavigationKey
import kotlinx.parcelize.Parcelize

@Parcelize
object CreateGroup: NavigationKey.WithResult<Group>

@Parcelize
data class ModifyGroup(
    val group: Group
): NavigationKey.WithResult<Group>

@Parcelize
data class FindUser(
    val excluded: List<User>
): NavigationKey.WithResult<User>
