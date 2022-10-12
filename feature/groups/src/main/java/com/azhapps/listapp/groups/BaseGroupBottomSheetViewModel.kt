package com.azhapps.listapp.groups

import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.groups.model.GroupBottomSheetAction
import com.azhapps.listapp.groups.model.GroupBottomSheetState
import com.azhapps.listapp.groups.navigation.FindUser
import dev.enro.core.result.registerForNavigationResult

abstract class BaseGroupBottomSheetViewModel : BaseViewModel<GroupBottomSheetState, GroupBottomSheetAction>() {

    private val findUserResult by registerForNavigationResult<User> {
        addMember(it)
    }

    override fun dispatch(action: GroupBottomSheetAction) {
        when (action) {
            is GroupBottomSheetAction.ConfirmRemoveMember -> updateState {
                copy(
                    showConfirmRemoveRememberDialog = true,
                    currentlySelectedUser = action.user,
                )
            }
            GroupBottomSheetAction.DismissDialog -> dismissConfirmRemoveMemberDialog()
            GroupBottomSheetAction.FindMember -> findUserResult.open(FindUser(state.currentMembers))
            is GroupBottomSheetAction.RemoveMember -> {
                dismissConfirmRemoveMemberDialog()
                removeMember(action.user)
            }
            is GroupBottomSheetAction.UpdateName -> updateState {
                copy(
                    currentName = action.newName
                )
            }
            GroupBottomSheetAction.Finalize -> finish()
        }
    }

    abstract fun addMember(user: User)

    abstract fun removeMember(user: User)

    abstract fun finish()

    private fun dismissConfirmRemoveMemberDialog() {
        updateState {
            copy(
                showConfirmRemoveRememberDialog = false
            )
        }
    }
}