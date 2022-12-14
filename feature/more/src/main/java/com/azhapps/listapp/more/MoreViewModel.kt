package com.azhapps.listapp.more

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeveloperMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import com.azhapps.listapp.account.AppUtils
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.BuildConfig
import com.azhapps.listapp.more.model.MoreAction
import com.azhapps.listapp.more.model.MoreLineState
import com.azhapps.listapp.more.model.MoreState
import com.azhapps.listapp.navigation.DeveloperOptions
import com.azhapps.listapp.navigation.Landing
import com.azhapps.listapp.network.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val tokenManager: TokenManager,
) : BaseViewModel<MoreState, MoreAction>() {

    private val navigationHandle by navigationHandle<Landing>()

    override fun initialState() = MoreState(
        items = mutableListOf(
            MoreLineState(
                title = R.string.more_option_settings_title,
                subtitle = R.string.more_option_settings_subtitle,
                icon = Icons.Filled.Settings,
                action = MoreAction.OpenSettings,
            ),
            MoreLineState(
                title = R.string.more_option_about_title,
                subtitle = R.string.more_option_about_subtitle,
                icon = Icons.Filled.Info,
                action = MoreAction.OpenAbout,
            ),
            MoreLineState(
                title = R.string.more_option_logout_title,
                subtitle = R.string.more_option_logout_subtitle,
                icon = Icons.Filled.Logout,
                action = MoreAction.OpenLogout,
            )
        ).apply {
            if (BuildConfig.DEBUG) {
                add(
                    MoreLineState(
                        title = R.string.more_option_developer_options_title,
                        icon = Icons.Filled.DeveloperMode,
                        action = MoreAction.OpenDeveloperOptions,
                    )
                )
            }
        }
    )

    override fun dispatch(action: MoreAction) {
        when (action) {
            MoreAction.OpenAbout -> navigationHandle.forward(About)
            MoreAction.OpenDeveloperOptions -> navigationHandle.forward(DeveloperOptions)
            MoreAction.OpenSettings -> navigationHandle.forward(Settings)
            MoreAction.OpenLogout -> updateState {
                copy(
                    showLogoutConfirmDialog = true
                )
            }
            MoreAction.CloseLogout -> {
                hideLogoutDialog()
            }
            MoreAction.DoLogout -> {
                hideLogoutDialog()
                tokenManager.clear()
                AppUtils.triggerRebirth()
            }
        }
    }

    private fun hideLogoutDialog() {
        updateState {
            copy(
                showLogoutConfirmDialog = false
            )
        }
    }
}