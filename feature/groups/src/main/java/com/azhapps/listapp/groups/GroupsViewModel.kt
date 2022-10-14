package com.azhapps.listapp.groups

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.uc.GetGroupsUseCase
import com.azhapps.listapp.groups.model.GroupItemState
import com.azhapps.listapp.groups.model.GroupsAction
import com.azhapps.listapp.groups.model.GroupsState
import com.azhapps.listapp.groups.navigation.CreateGroup
import com.azhapps.listapp.groups.navigation.ModifyGroup
import com.azhapps.listapp.groups.uc.CreateGroupUseCase
import com.azhapps.listapp.groups.uc.DeleteGroupUseCase
import com.azhapps.listapp.groups.uc.UpdateGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.registerForNavigationResult
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(
    val getGroupsUseCase: GetGroupsUseCase,
    val createGroupUseCase: CreateGroupUseCase,
    val updateGroupUseCase: UpdateGroupUseCase,
    val deleteGroupUseCase: DeleteGroupUseCase
) : BaseViewModel<GroupsState, GroupsAction>() {

    private val createGroupResult by registerForNavigationResult<Group> {
        createGroup(it)
    }

    private val modifyGroupResult by registerForNavigationResult<Group> {
        updateGroup(it)
    }

    init {
        dispatch(GroupsAction.GetGroups)
    }

    override fun initialState() = GroupsState()

    override fun dispatch(action: GroupsAction) {
        when (action) {
            GroupsAction.GetGroups -> getGroups()
            is GroupsAction.CreateGroup -> createGroupResult.open(CreateGroup)
            is GroupsAction.DeleteGroup -> {
                hideConfirmDeleteDialog()
                deleteGroup(action.group)
            }
            is GroupsAction.EditGroup -> modifyGroupResult.open(ModifyGroup(action.group))
            GroupsAction.HideConfirmDeleteDialog -> hideConfirmDeleteDialog()
            is GroupsAction.ShowConfirmDeleteDialog -> showConfirmDeleteDialog(action.group)
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

    private fun createGroup(group: Group) {
        updateState {
            copy(
                groupItemStates = groupItemStates.toMutableList().apply {
                    add(
                        GroupItemState(group, UiState.Loading)
                    )
                }
            )
        }
        viewModelScope.launch {
            val result = createGroupUseCase(group)
            if (result.success) {
                updateGroupItemState(group, UiState.Content)
            } else {
                updateGroupItemState(group, UiState.Error(result.error))
            }
        }
    }

    private fun updateGroup(group: Group) {
        updateGroupItemState(group, UiState.Loading)
        viewModelScope.launch {
            val result = updateGroupUseCase(group)
            if (result.success) {
                updateGroupItemState(result.data!!, UiState.Content)
            } else {
                updateGroupItemState(group, UiState.Error(result.error))
            }
        }
    }

    private fun updateGroupItemState(
        group: Group,
        newUiState: UiState,
    ) {
        updateState {
            copy(
                groupItemStates = groupItemStates.toMutableList().apply {
                    set(indexOfFirst {
                        it.group.name == group.name ||it.group.id == group.id
                    }, GroupItemState(group, newUiState))
                }
            )
        }
    }

    private fun deleteGroup(group: Group) {
        updateGroupItemState(group, UiState.Loading)
        viewModelScope.launch {
            val result = deleteGroupUseCase(group)
            if (result.success) {
                updateState {
                    copy(
                        groupItemStates = groupItemStates.toMutableList().apply {
                            removeIf {
                                it.group == group
                            }
                        }
                    )
                }
            } else {
                updateGroupItemState(group, UiState.Error(result.error))
            }
        }
    }

    private fun hideConfirmDeleteDialog() {
        updateState {
            copy(
                showConfirmDeleteGroupDialog = false,
                selectedGroup = null,
            )
        }
    }

    private fun showConfirmDeleteDialog(group: Group) {
        updateState {
            copy(
                showConfirmDeleteGroupDialog = true,
                selectedGroup = group,
            )
        }
    }
}
