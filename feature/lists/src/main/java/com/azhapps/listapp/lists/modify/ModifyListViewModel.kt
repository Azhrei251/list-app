package com.azhapps.listapp.lists.modify

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.modify.model.ModifyState
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
) : BaseModifyViewModel<ModifyList>() {

    override val navigationHandle by navigationHandle<ModifyList>()

    override fun buildStateFromNavKey(): ModifyState = navigationHandle.key.informativeList.let {
        ModifyState(
            currentName = it.name,
            currentCategoryName = it.category?.name ?: "",
            currentGroupName = it.group?.name ?: "",
            editable = it.isOwnedBySelf(),
        )
    }

    override suspend fun createCategory(name: String) = createListCategoryUseCase(name)

    override suspend fun getCategories() = getListCategoriesUseCase()

    override suspend fun getGroups() = getGroupsUseCase()

    override fun finalize() {
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
                name = state.currentName,
                category = category,
                group = group,
            )
            navigationHandle.closeWithResult(newList)
        }
    }
}

