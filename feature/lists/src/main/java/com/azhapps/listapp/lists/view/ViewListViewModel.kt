package com.azhapps.listapp.lists.view

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.ListItem
import com.azhapps.listapp.lists.navigation.ModifyItem
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.selection.UNCATEGORIZED
import com.azhapps.listapp.lists.selection.mapByItemCategory
import com.azhapps.listapp.lists.view.model.ItemCategoryState
import com.azhapps.listapp.lists.view.model.ListItemState
import com.azhapps.listapp.lists.view.model.ViewListAction
import com.azhapps.listapp.lists.view.model.ViewListState
import com.azhapps.listapp.lists.view.uc.CreateListItemUseCase
import com.azhapps.listapp.lists.view.uc.DeleteListItemUseCase
import com.azhapps.listapp.lists.view.uc.UpdateListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.registerForNavigationResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewListViewModel @Inject constructor(
    val createListItemUseCase: CreateListItemUseCase,
    val updateListItemUseCase: UpdateListItemUseCase,
    val deleteListItemUseCase: DeleteListItemUseCase,
) : BaseViewModel<ViewListState, ViewListAction>() {
    private val navigationHandle by navigationHandle<ViewList>()

    private val createItemResult by registerForNavigationResult<Pair<Boolean, ListItem>> {
        if (it.first) {
            deleteItem(it.second.id)
        } else {
            createItem(it.second)
        }
    }

    private val modifyItemResult by registerForNavigationResult<Pair<Boolean, ListItem>> {
        if (it.first) {
            deleteItem(it.second.id)
        } else {
            editItem(it.second)
        }
    }

    override fun initialState() = navigationHandle.key.informativeList.let { informativeList ->
        ViewListState(
            itemStates = informativeList.items.mapByItemCategory(),
            listTitle = informativeList.name,
            listCategory = informativeList.category?.name ?: UNCATEGORIZED
        )
    }

    override fun dispatch(action: ViewListAction) {
        when (action) {
            is ViewListAction.CreateItem -> createItemResult.open(
                ModifyItem(
                    ListItem(
                        category = action.category
                    )
                )
            )

            is ViewListAction.ModifyItem -> modifyItemResult.open(ModifyItem(action.item))

            is ViewListAction.ToggleComplete -> editItem(
                action.item.copy(
                    completed = !action.item.completed
                )
            )

            is ViewListAction.ToggleCollapsed -> toggleCategoryCollapsed(action.category)
        }
    }

    private fun editItem(itemToEdit: ListItem) {
        viewModelScope.launch {
            updateItemUiState(itemToEdit.id, UiState.Loading)

            val apiResult = updateListItemUseCase(itemToEdit, navigationHandle.key.informativeList.id)
            if (apiResult.success) {
                updateState {
                    copy(
                        itemStates = itemStates.toMutableList().apply {
                            val i = indexOf(firstOrNull {
                                it.id == itemToEdit.id
                            })
                            set(i, apiResult.data!!)
                        }.mapByItemCategory()
                    )
                }
            } else {
                updateItemUiState(itemToEdit.id, UiState.Error())
            }
        }
    }

    private fun createItem(itemToCreate: ListItem) {
        viewModelScope.launch {
            updateItemUiState(itemToCreate.id, UiState.Loading)

            val apiResult = createListItemUseCase(itemToCreate, navigationHandle.key.informativeList.id)
            if (apiResult.success) {
                updateState {
                    copy(
                        itemStates = itemStates.toMutableList().apply {
                            add(apiResult.data!!)
                        }.mapByItemCategory()
                    )
                }
            } else {
                updateItemUiState(itemToCreate.id, UiState.Error())
            }
        }
    }

    private fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            val result = deleteListItemUseCase(itemId)
            if (result.success) {
                val newList = state.itemStates
                newList.forEach { entry ->
                    entry.value.items.forEach {
                        if (it.item.id == itemId) {
                            val mutableList = entry.value.items.toMutableList()
                            mutableList.remove(it)
                            updateState {
                                copy(
                                    itemStates = state.itemStates.toMutableMap().apply {
                                        set(
                                            entry.key, entry.value.copy(
                                                items = mutableList
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                updateItemUiState(itemId, UiState.Error())
            }
        }
    }

    private fun updateItemUiState(
        itemId: Int,
        newUiState: UiState,
    ) {
        state.itemStates.forEach {
            var newList: MutableList<ListItemState>? = null
            it.value.items.forEachIndexed { i, item ->
                if (item.item.id == itemId) {
                    newList = it.value.items.toMutableList()
                    newList!![i] = item.copy(
                        uiState = newUiState
                    )
                }
            }
            if (newList != null) {
                updateState {
                    copy(
                        itemStates = itemStates.toMutableMap().apply {
                            set(
                                it.key, it.value.copy(
                                    items = newList!!
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    private fun toggleCategoryCollapsed(
        category: Category?
    ) {
        val name = category?.name ?: UNCATEGORIZED
        val categoryToUpdate = state.itemStates[name]
        if (categoryToUpdate != null) {
            updateState {
                copy(
                    itemStates = itemStates.toMutableMap().apply {
                        set(
                            name, categoryToUpdate.copy(
                                collapsed = !categoryToUpdate.collapsed
                            )
                        )
                    }
                )
            }
        }
    }
}

private fun Map<String, ItemCategoryState>.toMutableList(): MutableList<ListItem> = flatMap {
    it.value.items.map { state ->
        state.item
    }
}.toMutableList()