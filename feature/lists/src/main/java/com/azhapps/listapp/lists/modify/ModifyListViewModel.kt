package com.azhapps.listapp.lists.modify

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.account.DataModule.DEFAULT_GROUP_KEY
import com.azhapps.listapp.common.uc.GetGroupsUseCase
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.modify.model.ModifyState
import com.azhapps.listapp.lists.navigation.ModifyList
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyListViewModel @Inject constructor(
    private val getGroupsUseCase: GetGroupsUseCase,
    private val sharedPreferences: SharedPreferences,
) : BaseModifyViewModel<ModifyList>() {

    override val navigationHandle by navigationHandle<ModifyList>()

    override fun buildStateFromNavKey(): ModifyState = navigationHandle.key.informativeList.let {
        ModifyState(
            currentName = it.name,
            currentCategory = it.category,
            currentGroupName = it.group?.name ?: "",
            editable = it.isOwnedBySelf(),
            canDelete = navigationHandle.key.canDelete,
            isCreate = navigationHandle.key.isCreate,
        )
    }

    override suspend fun getGroups() = getGroupsUseCase()

    override fun finalize(deleted: Boolean) {
        if (deleted) {
            navigationHandle.closeWithResult(Pair(true, navigationHandle.key.informativeList))
        } else {
            viewModelScope.launch {
                val currentList = navigationHandle.key.informativeList
                val category = state.currentCategory
                val group = if (state.currentGroupName.isNotBlank()) state.availableGroups.firstOrNull {
                    it.name == state.currentGroupName
                } else null

                val newList = currentList.copy(
                    name = state.currentName,
                    category = category,
                    group = group,
                )
                navigationHandle.closeWithResult(Pair(false, newList))
            }
        }
    }

    override fun onGroupsUpdate() {
        if (state.currentGroupName.isEmpty()) {
            sharedPreferences.getInt(DEFAULT_GROUP_KEY, -1).takeIf { it != -1 }?.let { defaultId ->
                updateState {
                    copy(
                        currentGroupName = availableGroups.firstOrNull {
                            it.id == defaultId
                        }?.name ?: ""
                    )
                }
            }
        }
    }
}

