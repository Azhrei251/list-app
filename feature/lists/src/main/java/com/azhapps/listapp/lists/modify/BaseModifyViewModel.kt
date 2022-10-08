package com.azhapps.listapp.lists.modify

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.modify.model.ModifyAction
import com.azhapps.listapp.lists.modify.model.ModifyState
import com.azhapps.listapp.network.model.ApiResult
import dev.enro.core.NavigationKey
import dev.enro.core.TypedNavigationHandle
import kotlinx.coroutines.launch

abstract class BaseModifyViewModel<T : NavigationKey> : BaseViewModel<ModifyState, ModifyAction>() {

    abstract val navigationHandle: TypedNavigationHandle<T>

    override fun initialState(): ModifyState {
        updateCategories()
        updateGroups()
        return buildStateFromNavKey()
    }

    override fun dispatch(action: ModifyAction) {
        when (action) {
            is ModifyAction.UpdateCategory -> updateState {
                copy(
                    currentCategoryName = action.newCategoryName
                )
            }

            is ModifyAction.CreateCategory -> createAndUpdateCategory(action.name)

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
        }
    }

    private fun updateCategories() {
        viewModelScope.launch {
            val categoriesApiResult = getCategories()
            val categories = if (categoriesApiResult.success) {
                categoriesApiResult.data!!
            } else {
                Log.e(logTag, "Failed to get categories", categoriesApiResult.error)
                emptyList()
            }
            updateState {
                copy(
                    categoryUiState = UiState.Content,
                    availableCategories = categories
                )
            }
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
        }
    }

    private fun createAndUpdateCategory(name: String) {
        viewModelScope.launch {
            val result = createCategory(name = name)
            if (result.success) {
                updateState {
                    copy(
                        categoryUiState = UiState.Content,
                        availableCategories = mutableListOf<Category>().apply {
                            addAll(availableCategories)
                            add(result.data!!)
                        }
                    )
                }
            } else {
                Log.e(logTag, "Failed to create category", result.error)
                updateState {
                    copy(
                        categoryUiState = UiState.Content
                    )
                }
            }
        }
    }

    abstract fun finalize(deleted: Boolean)

    abstract suspend fun createCategory(name: String): ApiResult<Category>

    abstract fun buildStateFromNavKey(): ModifyState

    abstract suspend fun getCategories(): ApiResult<List<Category>>

    abstract suspend fun getGroups(): ApiResult<List<Group>>
}