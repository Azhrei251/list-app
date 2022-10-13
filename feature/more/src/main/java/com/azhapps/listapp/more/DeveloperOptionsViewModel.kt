package com.azhapps.listapp.more

import android.content.SharedPreferences
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.more.model.DeveloperOptionsAction
import com.azhapps.listapp.more.model.DeveloperOptionsState
import com.azhapps.listapp.network.model.Environment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeveloperOptionsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): BaseViewModel<DeveloperOptionsState, DeveloperOptionsAction>(){

    override fun initialState() = DeveloperOptionsState()

    override fun dispatch(action: DeveloperOptionsAction) {
        when (action) {
            is DeveloperOptionsAction.UpdateEnvironment -> {
                Environment.set(sharedPreferences, action.environment)
                updateState {
                    copy(
                        currentEnvironment = action.environment
                    )
                }
            }
        }
    }
}