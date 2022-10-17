package com.azhapps.listapp.more.model

sealed interface MoreAction {
    object OpenSettings : MoreAction

    object OpenDeveloperOptions : MoreAction

    object OpenAbout : MoreAction

    object OpenLogout : MoreAction

    object CloseLogout : MoreAction

    object DoLogout : MoreAction
}