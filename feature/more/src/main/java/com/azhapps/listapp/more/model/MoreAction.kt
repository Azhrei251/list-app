package com.azhapps.listapp.more.model

sealed interface MoreAction {
    object OpenSettings: MoreAction

    object OpenDeveloperOptions: MoreAction

    object OpenAbout: MoreAction
}