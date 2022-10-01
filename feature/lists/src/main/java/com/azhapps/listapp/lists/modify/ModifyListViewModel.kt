package com.azhapps.listapp.lists.modify

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.modify.model.ModifyListAction
import com.azhapps.listapp.lists.modify.model.ModifyListState
import com.azhapps.listapp.lists.modify.uc.CreateListCategoryUseCase
import com.azhapps.listapp.lists.modify.uc.GetGroupsUseCase
import com.azhapps.listapp.lists.modify.uc.GetListCategoriesUseCase
import com.azhapps.listapp.lists.navigation.ModifyList
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyListViewModel @Inject constructor(
    private val getListCategoriesUseCase: GetListCategoriesUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val createListCategoryUseCase: CreateListCategoryUseCase,
) : BaseViewModel<ModifyListState, ModifyListAction>() {

    private val navigationHandle by navigationHandle<ModifyList>()
    private val logTag = ModifyListViewModel::class.java.simpleName

    override fun initialState() = navigationHandle.key.informativeList.let {
        fetchData()

        ModifyListState(
            currentListName = it.name,
            currentCategoryName = it.category?.name ?: "",
            currentGroupName = it.group?.name ?: "",
            editable = it.isOwnedBySelf(),
        )
    }

    override fun dispatch(action: ModifyListAction) {
        when (action) {
            is ModifyListAction.UpdateCategory -> updateState {
                copy(
                    currentCategoryName = action.newCategoryName
                )
            }

            is ModifyListAction.CreateCategory -> createListCategory(action.name)

            is ModifyListAction.UpdateListName -> updateState {
                copy(
                    currentListName = action.newListName
                )
            }

            is ModifyListAction.UpdateGroup -> updateState {
                copy(
                    currentGroupName = action.newGroupName
                )
            }
            ModifyListAction.Finalize -> {
                viewModelScope.launch {
                    val currentList = navigationHandle.key.informativeList
                    val category = if (state.currentCategoryName.isNotBlank()) {
                        state.availableCategories.firstOrNull {
                            it.name == state.currentCategoryName
                        } ?: createListCategoryUseCase(state.currentCategoryName).data
                    } else null
                    val group = if (state.currentGroupName.isNotBlank()) state.availableGroups.firstOrNull {
                        it.name == state.currentGroupName
                    } else null

                    val newList = currentList.copy(
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

