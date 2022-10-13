package com.azhapps.listapp.groups

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.groups.model.GroupBottomSheetState
import com.azhapps.listapp.groups.navigation.CreateGroup
import com.azhapps.listapp.common.uc.GetOwnUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    val getOwnUserInfoUseCase: GetOwnUserInfoUseCase,
) : BaseGroupBottomSheetViewModel() {

    private val navigationHandle by navigationHandle<CreateGroup>()

    init {
        viewModelScope.launch {
            val result = getOwnUserInfoUseCase()
            if (result.success) {
                updateState {
                    copy(
                        currentMembers = currentMembers.toMutableList().apply {
                            add(result.data!!)
                        }
                    )
                }
            }
        }
    }

    override fun initialState() = GroupBottomSheetState()

    override fun addMember(user: User) {
        updateState {
            copy(
                currentMembers = currentMembers.toMutableList().apply {
                    add(user)
                }
            )
        }
    }

    override fun removeMember(user: User) {
        updateState {
            copy(
                currentMembers = currentMembers.filter {
                    it.id != user.id
                }
            )
        }
    }

    override fun finish() {
        navigationHandle.closeWithResult(
            Group(
                name = state.currentName,
                users = state.currentMembers,
            )
        )
    }
}