package com.azhapps.listapp.lists.modify

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.lists.modify.model.ModifyAction
import com.azhapps.listapp.lists.modify.model.ModifyState
import com.azhapps.listapp.lists.navigation.SelectCategory
import com.azhapps.listapp.network.model.ApiResult
import dev.enro.core.NavigationKey
import dev.enro.core.TypedNavigationHandle
import dev.enro.core.result.registerForNavigationResult
import kotlinx.coroutines.launch

abstract class BaseModifyViewModel<T : NavigationKey> : BaseViewModel<ModifyState, ModifyAction>() {

    private val selectCategoryResult by registerForNavigationResult<Category> {
        dispatch(ModifyAction.UpdateCategory(it))
    }

    abstract val navigationHandle: TypedNavigationHandle<T>

    override fun initialState(): ModifyState {
        updateGroups()
        return buildStateFromNavKey()
    }

    override fun dispatch(action: ModifyAction) {
        when (action) {
            is ModifyAction.UpdateCategory -> updateState {
                copy(
                    currentCategory = action.newCategory
                )
            }

            is ModifyAction.UpdateListName -> updateState {
                copy(
                    currentName = action.newListName
                )
            }

            is ModifyAction.UpdateGroup -> updateState {
                copy(
                    currentGroupName = action.newGroupName
                )
            }

            is ModifyAction.Finalize -> finalize(action.deleted)

            is ModifyAction.SelectCategory -> selectCategoryResult.open(SelectCategory(state.currentCategory))
        }
    }

    private fun updateGroups() {
        viewModelScope.launch {
            val groupsApiResult = getGroups()
            val groups = if (groupsApiResult.success) {
                groupsApiResult.data!!
            } else {
                Log.e(logTag, "Failed to get groups", groupsApiResult.error)
                emptyList()
            }
            updateState {
                copy(
                    groupUiState = UiState.Content,
                    availableGroups = groups
                )
            }
            onGroupsUpdate()
        }
    }

    open fun onGroupsUpdate() {
        //Empty hook
    }

    abstract fun finalize(deleted: Boolean)

    abstract fun buildStateFromNavKey(): ModifyState

    abstract suspend fun getGroups(): ApiResult<List<Group>>
}