package com.azhapps.listapp.landing.model

sealed interface LandingAction {
    data class GoToTab(
        val tab: LandingState.Tab
    ) : LandingAction
}