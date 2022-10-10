package com.azhapps.listapp.landing

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.landing.model.LandingAction
import com.azhapps.listapp.landing.model.LandingState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(): BaseViewModel<LandingState, LandingAction>() {

    override fun initialState() = LandingState()

    override fun dispatch(action: LandingAction) {
        when (action) {
            is LandingAction.GoToTab -> updateState {
                copy(
                    currentTab = action.tab
                )
            }
        }
    }
}