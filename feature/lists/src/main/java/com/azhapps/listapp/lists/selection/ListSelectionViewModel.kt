package com.azhapps.listapp.lists.selection

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.selection.uc.CreateInformativeListUseCase
import com.azhapps.listapp.lists.modify.uc.UpdateInformativeListUseCase
import com.azhapps.listapp.lists.navigation.ListSelection
import com.azhapps.listapp.lists.navigation.ModifyList
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import com.azhapps.listapp.lists.selection.model.ListSelectionItemState
import com.azhapps.listapp.lists.selection.model.ListSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.forward
import dev.enro.core.result.registerForNavigationResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSelectionViewModel @Inject constructor(
    val getPersonalListsUseCase: GetPersonalListsUseCase,
    val updateInformativeListUseCase: UpdateInformativeListUseCase,
    val createInformativeListUseCase: CreateInformativeListUseCase,
) : BaseViewModel<ListSelectionState, ListSelectionAction>() {
    private val navigationHandle by navigationHandle<ListSelection>()

    init {
        dispatch(ListSelectionAction.GetAllLists)
    }

    private val editListResult by registerForNavigationResult<InformativeList> {
        editList(it)
    }
    private val createListResult by registerForNavigationResult<InformativeList> {
        createList(it)
    }

    override fun dispatch(action: ListSelectionAction) {
        when (action) {
            is ListSelectionAction.GetAllLists -> getAllLists()

            is ListSelectionAction.EditList -> editListResult.open(ModifyList(action.informativeList))

            is ListSelectionAction.CreateList -> createListResult.open(
                ModifyList(
                    InformativeList(
                        name = "",
                        category = action.category,
                        group = null,
                    )
                )
            )

            is ListSelectionAction.ShowList -> {
                navigationHandle.forward(ViewList(action.informativeList))
            }
        }
    }

    override fun initialState() = ListSelectionState()

    private fun getAllLists() {
        viewModelScope.launch {
            val result = getPersonalListsUseCase()

            if (result.success) {
                updateState {
                    copy(
                        informativeListMap = result.data!!.mapByListCategory().toMutableMap(),
                    )
                }
            } else {
                //TODO
            }
        }
    }

    private fun editList(updatedList: InformativeList) {
        viewModelScope.launch {
            updateItemUiState(updatedList.id, UiState.Loading)

            val apiResult = updateInformativeListUseCase(updatedList)
            if (apiResult.success) {
                updateState {
                    copy(
                        informativeListMap = informativeListMap.toMutableList().apply {
                            val i = indexOf(firstOrNull {
                                it.id == updatedList.id
                            })
                            set(i, apiResult.data!!)
                        }.mapByListCategory()
                    )
                }
            } else {
                updateItemUiState(updatedList.id, UiState.Error())
            }
        }
    }

    private fun createList(updatedList: InformativeList) {
        viewModelScope.launch {
            updateItemUiState(updatedList.id, UiState.Loading)

            val apiResult = createInformativeListUseCase(updatedList)
            if (apiResult.success) {
                updateState {
                    copy(
                        informativeListMap = informativeListMap.toMutableList().apply {
                            add(apiResult.data!!)
                        }.mapByListCategory()
                    )
                }
            } else {
                updateItemUiState(updatedList.id, UiState.Error())
            }
        }
    }

    private fun updateItemUiState(
        listId: Int,
        newUiState: UiState,
    ) {
        state.informativeListMap.forEach {
            var newList: MutableList<ListSelectionItemState>? = null
            it.value.forEachIndexed { i, item ->
                if (item.informativeList.id == listId) {
                    newList = it.value.toMutableList()
                    newList!![i] = item.copy(
                        uiState = newUiState
                    )
                }
            }
            if (newList != null) {
                updateState {
                    copy(
                        informativeListMap = informativeListMap.toMutableMap().apply {
                            set(it.key, newList!!)
                        }
                    )
                }
            }
        }
    }
}

private fun Map<String, List<ListSelectionItemState>>.toMutableList(): MutableList<InformativeList> = flatMap {
    it.value.map { itemState ->
        itemState.informativeList
    }
}.toMutableList()