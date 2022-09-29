package com.azhapps.listapp.lists.selection

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import com.azhapps.listapp.lists.selection.model.ListSelectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListSelectionViewModel @Inject constructor(
    val getPersonalListsUseCase: GetPersonalListsUseCase
): BaseViewModel<ListSelectionState, ListSelectionAction>() {

    override fun dispatch(action: ListSelectionAction) {
       when(action) {
           ListSelectionAction.GetAllLists -> getAllLists()
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
                        personalLists = result.data!!,
                    )
                }
            }
        }
    }
}