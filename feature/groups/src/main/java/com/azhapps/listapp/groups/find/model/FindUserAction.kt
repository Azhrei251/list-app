package com.azhapps.listapp.groups.find.model

import com.azhapps.listapp.common.model.User

sealed interface FindUserAction {
    data class SearchUsers(
        val filterString: String = ""
    ): FindUserAction

    data class Select(
        val user: User
    ): FindUserAction

    object Clear: FindUserAction
}