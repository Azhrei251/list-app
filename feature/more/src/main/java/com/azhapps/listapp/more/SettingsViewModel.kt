package com.azhapps.listapp.more

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.account.DataModule.DEFAULT_GROUP_KEY
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.uc.GetGroupsUseCase
import com.azhapps.listapp.more.model.SettingsAction
import com.azhapps.listapp.more.model.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val getGroupsUseCase: GetGroupsUseCase,
) : BaseViewModel<SettingsState, SettingsAction>() {

    init {
        dispatch(SettingsAction.GetAvailableGroups)
    }

    override fun initialState() = SettingsState()

    override fun dispatch(action: SettingsAction) {
        when (action) {
            SettingsAction.GetAvailableGroups -> getGroups()
            is SettingsAction.UpdateDefaultGroup -> updateDefaultGroup(action.group)
        }
    }

    private fun getGroups() {
        updateState {
            copy(
                uiState = UiState.Loading
            )
        }
        viewModelScope.launch {
            val result = getGroupsUseCase()
            if (result.success) {
                updateState {
                    copy(
                        uiState = UiState.Content,
                        availableGroups = result.data!!,
                        defaultGroup = sharedPreferences.getInt(DEFAULT_GROUP_KEY, -1).takeIf { it != -1 }?.let { defaultId ->
                            result.data?.firstOrNull {
                                it.id == defaultId
                            }
                        }
                    )
                }
            } else {
                updateState {
                    copy(
                        uiState = UiState.Error(result.error)
                    )
                }
            }
        }
    }

    private fun updateDefaultGroup(group: Group) {
        sharedPreferences.edit().putInt(DEFAULT_GROUP_KEY, group.id).apply()
        updateState {
            copy(
                defaultGroup = group
            )
        }
    }
}