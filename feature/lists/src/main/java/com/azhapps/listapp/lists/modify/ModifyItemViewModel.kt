package com.azhapps.listapp.lists.modify

import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.lists.modify.model.ModifyState
import com.azhapps.listapp.lists.modify.uc.CreateItemCategoryUseCase
import com.azhapps.listapp.lists.modify.uc.GetItemCategoriesUseCase
import com.azhapps.listapp.lists.navigation.ModifyItem
import com.azhapps.listapp.network.model.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyItemViewModel @Inject constructor(
    val getItemCategoriesUseCase: GetItemCategoriesUseCase,
    val createItemCategoryUseCase: CreateItemCategoryUseCase,
) : BaseModifyViewModel<ModifyItem>() {

    override val navigationHandle by navigationHandle<ModifyItem>()

    override fun buildStateFromNavKey() = navigationHandle.key.listItem.let {
        ModifyState(
            currentName = it.itemText,
            currentCategoryName = it.category?.name ?: "",
            canDelete = navigationHandle.key.canDelete,
        )
    }

    override suspend fun createCategory(name: String): ApiResult<Category> = createItemCategoryUseCase(name)

    override suspend fun getCategories() = getItemCategoriesUseCase()

    override suspend fun getGroups(): ApiResult<List<Group>> = ApiResult(true, emptyList())

    override fun finalize(deleted: Boolean) {
        if (deleted) {
            navigationHandle.closeWithResult(Pair(true, navigationHandle.key.listItem))
        } else {
            viewModelScope.launch {
                val currentItem = navigationHandle.key.listItem
                val category = if (state.currentCategoryName.isNotBlank()) {
                    state.availableCategories.firstOrNull {
                        it.name == state.currentCategoryName
                    } ?: createItemCategoryUseCase(state.currentCategoryName).data
                } else null

                val newItem = currentItem.copy(
                    itemText = state.currentName,
                    category = category,
                )
                navigationHandle.closeWithResult(Pair(false, newItem))
            }
        }
    }
}