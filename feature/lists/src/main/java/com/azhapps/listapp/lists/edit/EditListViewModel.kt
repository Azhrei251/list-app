package com.azhapps.listapp.lists.edit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.edit.model.EditListAction
import com.azhapps.listapp.lists.edit.model.EditListState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.navigation.EditList
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditListViewModel @Inject constructor(
    private val getListCategoriesUseCase: GetListCategoriesUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val createListCategoryUseCase: CreateListCategoryUseCase,
) : BaseViewModel<EditListState, EditListAction>() {

    private val navigationHandle by navigationHandle<EditList>()
    private val logTag = EditListViewModel::class.java.simpleName

    override fun initialState() = navigationHandle.key.informativeList.let {
        fetchData()

        EditListState(
            currentListName = it.name,
            currentCategoryName = it.category?.name ?: "",
            currentGroupName = it.group?.name ?: "",
            editable = it.isOwnedBySelf(),
        )
    }

    override fun dispatch(action: EditListAction) {
        when (action) {
            is EditListAction.UpdateCategory -> updateState {
                copy(
                    currentCategoryName = action.newCategoryName
                )
            }

            is EditListAction.CreateCategory -> createListCategory(action.name)

            is EditListAction.UpdateListName -> updateState {
                copy(
                    currentListName = action.newListName
                )
            }

            is EditListAction.UpdateGroup -> updateState {
                copy(
                    currentGroupName = action.newGroupName
                )
            }
            EditListAction.Finalize -> {
                viewModelScope.launch {
                    val currentList = navigationHandle.key.informativeList
                    val category = if (state.currentCategoryName.isNotBlank()) {
                        state.availableCategories.firstOrNull {
                            it.name == state.currentCategoryName
                        } ?: createListCategoryUseCase(state.currentCategoryName).data
                    } else currentList.category
                    val group = if (state.currentGroupName.isNotBlank()) state.availableGroups.firstOrNull {
                        it.name == state.currentGroupName
                    } else currentList.group

                    val newList = navigationHandle.key.informativeList.copy(
                        name = state.currentListName,
                        category = category,
                        group = group,
                    )
                    navigationHandle.closeWithResult(newList)
                }
            }
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val categoriesApiResult = getListCategoriesUseCase()
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
        viewModelScope.launch {
            val categoriesApiResult = getGroupsUseCase()
            val categories = if (categoriesApiResult.success) {
                categoriesApiResult.data!!
            } else {
                Log.e(logTag, "Failed to get groups", categoriesApiResult.error)
                emptyList()
            }
            updateState {
                copy(
                    groupUiState = UiState.Content,
                    availableGroups = categories
                )
            }
        }
    }

    private fun createListCategory(
        name: String,
    ) {
        updateState {
            copy(categoryUiState = UiState.Loading)
        }
        viewModelScope.launch {
            val result = createListCategoryUseCase(name)
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
}

