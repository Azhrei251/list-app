package com.azhapps.listapp.landing.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.azhapps.listapp.R
import com.azhapps.listapp.common.UiState

data class LandingState(
    val uiState: UiState = UiState.Content,
    val currentTab: Tab = Tab.LISTS,
) {
    enum class Tab(
        @StringRes val buttonText: Int,
        @DrawableRes val icon: Int,
    ) {
        LISTS(R.string.landing_button_lists, R.drawable.ic_nav_lists),
        GROUPS(R.string.landing_button_groups, R.drawable.ic_nav_groups),
        MORE(R.string.landing_button_more, R.drawable.ic_nav_more),
    }
}
