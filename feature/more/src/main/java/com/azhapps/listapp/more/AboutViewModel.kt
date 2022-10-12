package com.azhapps.listapp.more

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.more.ui.AboutAction
import com.azhapps.listapp.more.ui.AboutState
import javax.inject.Inject

class AboutViewModel @Inject constructor(): BaseViewModel<AboutState, AboutAction>() {

    override fun initialState() = AboutState()

    override fun dispatch(action: AboutAction) {
        when(action) {
            AboutAction.HidePrivacyPolicy -> updateState {
                copy(
                    showPrivacyPolicy = false
                )
            }
            AboutAction.ShowPrivacyPolicy -> updateState {
                copy(
                    showPrivacyPolicy = true
                )
            }
        }
    }
}