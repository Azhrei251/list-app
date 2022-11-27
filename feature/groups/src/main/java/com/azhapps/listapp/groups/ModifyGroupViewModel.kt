package com.azhapps.listapp.groups

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.groups.model.GroupBottomSheetState
import com.azhapps.listapp.groups.navigation.ModifyGroup
import com.azhapps.listapp.groups.uc.AddUserToGroupUseCase
import com.azhapps.listapp.groups.uc.RemoveUserFromGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyGroupViewModel @Inject constructor(
    val removeUserFromGroupUseCase: RemoveUserFromGroupUseCase,
    val addUserToGroupUseCase: AddUserToGroupUseCase,
) : BaseGroupBottomSheetViewModel() {

    private val navigationHandle by navigationHandle<ModifyGroup>()

    override fun initialState() = GroupBottomSheetState(
        currentName = navigationHandle.key.group.name,
        currentMembers = navigationHandle.key.group.users ?: emptyList(),
    )

    override fun addMember(user: User) {
        viewModelScope.launch {
            val result = addUserToGroupUseCase(
                userId = user.id,
                groupId = navigationHandle.key.group.id
            )
            if (result.success) {
                updateState {
                    copy(
                        currentMembers = currentMembers.toMutableList().apply {
                            add(user)
                        }
                    )
                }
                notifyGroupUpdate()
            } else {
                //TODO error handle
            }
        }
    }

    override fun removeMember(user: User) {
        viewModelScope.launch {
            val result = removeUserFromGroupUseCase(
                userId = user.id,
                groupId = navigationHandle.key.group.id
            )
            if (result.success) {
                updateState {
                    copy(
                        currentMembers = currentMembers.filter {
                            it.id != user.id
                        }
                    )
                }
                notifyGroupUpdate()
            } else {
                //TODO error handle
            }
        }
    }

    override fun finish() {
        navigationHandle.closeWithResult(
            navigationHandle.key.group.copy(
                name = state.currentName,
                users = state.currentMembers,
            )
        )
    }

    private suspend fun notifyGroupUpdate() {
        GroupsSharedStateManager.dispatch(GroupsSharedStateManager.Event.GroupUpdate(navigationHandle.key.group.copy(
            users = state.currentMembers
        )))
    }
}
