package com.azhapps.listapp.lists.selection

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.ListsSharedStateManager
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.modify.uc.UpdateInformativeListUseCase
import com.azhapps.listapp.lists.navigation.ModifyList
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import com.azhapps.listapp.lists.selection.model.ListSelectionItemState
import com.azhapps.listapp.lists.selection.model.ListSelectionState
import com.azhapps.listapp.lists.selection.uc.CreateInformativeListUseCase
import com.azhapps.listapp.lists.selection.uc.DeleteInformativeListUseCase
import com.azhapps.listapp.lists.selection.uc.GetPersonalListsUseCase
import com.azhapps.listapp.navigation.Lists
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
    val deleteInformativeListUseCase: DeleteInformativeListUseCase,
) : BaseViewModel<ListSelectionState, ListSelectionAction>() {

    private val navigationHandle by navigationHandle<Lists>()

    init {
        dispatch(ListSelectionAction.GetAllLists)
        viewModelScope.launch {
            ListsSharedStateManager.flow.collect { event ->
                when (event) {
                    is ListsSharedStateManager.Event.ListDelete -> removeListFromState(event.listId)
                    is ListsSharedStateManager.Event.ListUpdate -> updateListInState(event.informativeList)
                }
            }
        }
    }

    private val editListResult by registerForNavigationResult<Pair<Boolean, InformativeList>> {
        if (it.first) {
            deleteList(it.second.id)
        } else {
            editList(it.second)
        }
    }
    private val createListResult by registerForNavigationResult<Pair<Boolean, InformativeList>> {
        if (it.first) {
            deleteList(it.second.id)
        } else {
            createList(it.second)
        }
    }

    override fun dispatch(action: ListSelectionAction) {
        when (action) {
            is ListSelectionAction.GetAllLists -> getAllLists()

            is ListSelectionAction.EditList -> editListResult.open(ModifyList(action.informativeList))

            is ListSelectionAction.CreateList -> createListResult.open(
                ModifyList(
                    informativeList = InformativeList(
                        name = "",
                        category = action.category,
                        group = null,
                        owner = SelectedAccount.currentAccountName!!
                    ),
                    canDelete = false,
                )
            )

            is ListSelectionAction.ShowList -> {
                navigationHandle.forward(ViewList(action.informativeList))
            }
        }
    }

    override fun initialState() = ListSelectionState()

    private fun getAllLists() {
        updateState {
            copy(
                uiState = UiState.Loading
            )
        }
        viewModelScope.launch {
            val result = getPersonalListsUseCase()

            if (result.success) {
                updateState {
                    copy(
                        uiState = UiState.Content,
                        informativeListMap = result.data!!.mapByListCategory().toMutableMap(),
                    )
                }
            } else {
                updateState {
                    copy(
                        uiState = UiState.Error(result.error)
                    )
                }
            }
        }
    }

    private fun editList(updatedList: InformativeList) {
        viewModelScope.launch {
            updateItemUiState(updatedList.id, UiState.Loading)

            val apiResult = updateInformativeListUseCase(updatedList)
            if (apiResult.success) {
                updateListInState(apiResult.data!!)
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

    private fun deleteList(
        listId: Int
    ) {
        viewModelScope.launch {
            val result = deleteInformativeListUseCase(listId)
            if (result.success) {
               removeListFromState(listId)
            } else {
                updateItemUiState(listId, UiState.Error())
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

    private fun updateListInState(updatedList: InformativeList) {
        updateState {
            copy(
                informativeListMap = informativeListMap.toMutableList().apply {
                    val i = indexOf(firstOrNull {
                        it.id == updatedList.id
                    })
                    set(i, updatedList)
                }.mapByListCategory()
            )
        }
    }

    private fun removeListFromState(listId: Int) {
        val newLists = state.informativeListMap.toMutableList()
        newLists.removeIf {
            it.id == listId
        }
        updateState {
            copy(
                informativeListMap = newLists.mapByListCategory()
            )
        }
    }
}

private fun Map<String, List<ListSelectionItemState>>.toMutableList(): MutableList<InformativeList> = flatMap {
    it.value.map { itemState ->
        itemState.informativeList
    }
}.toMutableList()