package com.azhapps.listapp.lists.selection

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.UpdateInformativeListUseCase
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.navigation.EditList
import com.azhapps.listapp.lists.navigation.ListSelection
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import com.azhapps.listapp.lists.selection.model.ListSelectionItemState
import com.azhapps.listapp.lists.selection.model.ListSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.registerForNavigationResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSelectionViewModel @Inject constructor(
    val getPersonalListsUseCase: GetPersonalListsUseCase,
    val updateInformativeListUseCase: UpdateInformativeListUseCase,
) : BaseViewModel<ListSelectionState, ListSelectionAction>() {
    private val navigationHandle by navigationHandle<ListSelection>()

    init {
        dispatch(ListSelectionAction.GetAllLists)
    }

    private val editListResult by registerForNavigationResult<InformativeList> { updatedList ->
        viewModelScope.launch {
            state.informativeListMap.forEach {
                var newList: MutableList<ListSelectionItemState>? = null
                it.value.forEachIndexed { i, item ->
                    if (item.informativeList.id == updatedList.id) {
                        newList = it.value.toMutableList()
                        newList!![i] = item.copy(
                            uiState = UiState.Loading
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

            val apiResult = updateInformativeListUseCase(updatedList)
            if (apiResult.success) {
                updateState {
                    copy(
                        informativeListMap = informativeListMap.flatMap {
                            it.value.map { itemState ->
                                itemState.informativeList
                            }
                        }.toMutableList().apply {
                            val i = indexOf(firstOrNull {
                                it.id == updatedList.id
                            })
                            set(i, apiResult.data!!)
                        }.mapByCategory()
                    )
                }
            } else {
                //TODO errors
            }
        }
    }

    override fun dispatch(action: ListSelectionAction) {
        when (action) {
            is ListSelectionAction.GetAllLists -> getAllLists()

            is ListSelectionAction.EditList -> {
                editListResult.open(EditList(action.informativeList))
            }

            else -> {
                //TODO
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
                        informativeListMap = result.data!!.mapByCategory().toMutableMap(),
                    )
                }
            } else {
                //TODO
            }
        }
    }
}