package com.azhapps.listapp.lists.modify

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.lists.modify.model.ModifyState
import com.azhapps.listapp.lists.navigation.ModifyItem
import com.azhapps.listapp.network.model.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyItemViewModel @Inject constructor() : BaseModifyViewModel<ModifyItem>() {

    override val navigationHandle by navigationHandle<ModifyItem>()

    override fun buildStateFromNavKey() = navigationHandle.key.listItem.let {
        ModifyState(
            currentName = it.itemText,
            currentCategory = it.category,
            canDelete = navigationHandle.key.canDelete,
            isCreate = navigationHandle.key.isCreate,
        )
    }


    override suspend fun getGroups(): ApiResult<List<Group>> = ApiResult(true, emptyList())

    override fun finalize(deleted: Boolean) {
        if (deleted) {
            navigationHandle.closeWithResult(Pair(true, navigationHandle.key.listItem))
        } else {
            viewModelScope.launch {
                val currentItem = navigationHandle.key.listItem
                val category = state.currentCategory

                val newItem = currentItem.copy(
                    itemText = state.currentName,
                    category = category,
                )
                navigationHandle.closeWithResult(Pair(false, newItem))
            }
        }
    }
}