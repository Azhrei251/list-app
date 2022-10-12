package com.azhapps.listapp.more

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.more.ui.AboutAction
import com.azhapps.listapp.more.ui.AboutState
import dev.enro.core.forward
import dev.enro.viewmodel.navigationHandle
import javax.inject.Inject

class AboutViewModel @Inject constructor() : BaseViewModel<AboutState, AboutAction>() {

    private val navigationHandle by navigationHandle<About>()

    override fun initialState() = AboutState()

    override fun dispatch(action: AboutAction) {
        when (action) {
            AboutAction.ShowPrivacyPolicy -> navigationHandle.forward(PrivacyPolicy)
        }
    }
}