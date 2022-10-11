package com.azhapps.listapp.groups

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.uc.GetGroupsUseCase
import com.azhapps.listapp.groups.model.GroupItemState
import com.azhapps.listapp.groups.model.GroupsAction
import com.azhapps.listapp.groups.model.GroupsState
import com.azhapps.listapp.groups.navigation.CreateGroup
import com.azhapps.listapp.groups.navigation.ModifyGroup
import com.azhapps.listapp.navigation.Groups
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    val getGroupsUseCase: GetGroupsUseCase
) : BaseViewModel<GroupsState, GroupsAction>() {

    private val navigationHandle by navigationHandle<Groups>()

    init {
        dispatch(GroupsAction.GetGroups)
    }

    override fun initialState() = GroupsState()

    override fun dispatch(action: GroupsAction) {
        when (action) {
            GroupsAction.GetGroups -> getGroups()
            is GroupsAction.CreateGroup -> navigationHandle.forward(CreateGroup)
            is GroupsAction.DeleteGroup -> TODO()
            is GroupsAction.EditGroup -> navigationHandle.forward(ModifyGroup(action.group))
        }
    }

    private fun getGroups() {
        viewModelScope.launch {
            val result = getGroupsUseCase()
            if (result.success) {
                updateState {
                    copy(
                        uiState = UiState.Content,
                        groupItemStates = result.data!!.map {
                            GroupItemState(it)
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
}