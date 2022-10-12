package com.azhapps.listapp.groups.find

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.groups.find.model.FindUserAction
import com.azhapps.listapp.groups.find.model.FindUserState
import com.azhapps.listapp.groups.find.uc.SearchUsersUseCase
import com.azhapps.listapp.groups.navigation.FindUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindUserViewModel @Inject constructor(
    val searchUsersUseCase: SearchUsersUseCase
) : BaseViewModel<FindUserState, FindUserAction>() {

    private val navigationHandle by navigationHandle<FindUser>()

    init {
        dispatch(FindUserAction.SearchUsers())
    }

    override fun initialState() = FindUserState()

    override fun dispatch(action: FindUserAction) {
        when (action) {
            is FindUserAction.SearchUsers -> searchUsers(action.filterString)
            is FindUserAction.Select -> navigationHandle.closeWithResult(action.user)
            FindUserAction.Clear -> searchUsers("")
        }
    }

    private fun searchUsers(filter: String) {
        updateState {
            copy(searchFilter = filter)
        }
        viewModelScope.launch {
            val result = searchUsersUseCase(filter)
            if (result.success) {
                updateState {
                    copy(
                        uiState = UiState.Content,
                        availableUsers = result.data!!.filter {
                            !navigationHandle.key.excluded.contains(it)
                        }
                    )
                }
            } else {
                updateState {
                    copy(
                        uiState = if (availableUsers.isEmpty()) UiState.Error() else UiState.Content,
                    )
                }
            }
        }
    }
}