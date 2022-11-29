package com.azhapps.listapp.lists.modify

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.azhapps.listapp.common.BaseViewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.modify.model.SelectCategoryAction
import com.azhapps.listapp.lists.modify.model.SelectCategoryState
import com.azhapps.listapp.lists.modify.uc.CreateListCategoryUseCase
import com.azhapps.listapp.lists.modify.uc.GetItemCategoriesUseCase
import com.azhapps.listapp.lists.navigation.SelectCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.enro.core.result.closeWithResult
import dev.enro.viewmodel.navigationHandle
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectCategoryViewModel @Inject constructor(
    private val getItemCategoriesUseCase: GetItemCategoriesUseCase,
    private val createListCategoryUseCase: CreateListCategoryUseCase,
) : BaseViewModel<SelectCategoryState, SelectCategoryAction>() {

    private val navigationHandle by navigationHandle<SelectCategory>()

    init {
        dispatch(SelectCategoryAction.LoadCategories)
    }

    override fun initialState() = SelectCategoryState(current = navigationHandle.key.current)

    override fun dispatch(action: SelectCategoryAction) {
        when (action) {
            is SelectCategoryAction.Filter -> updateState {
                copy(
                    filter = action.filter,
                )
            }
            is SelectCategoryAction.Select -> navigationHandle.closeWithResult(action.category)
            is SelectCategoryAction.LoadCategories -> updateCategories()
            is SelectCategoryAction.Create -> createAndUpdateCategory(action.name)
        }
    }

    private fun updateCategories() {
        viewModelScope.launch {
            val categoriesApiResult = getItemCategoriesUseCase()
            val categories = if (categoriesApiResult.success) {
                categoriesApiResult.data!!
            } else {
                Log.e(logTag, "Failed to get categories", categoriesApiResult.error)
                emptyList()
            }
            updateState {
                copy(
                    available = categories,
                    uiState = UiState.Content,
                )
            }
        }
    }

    private fun createAndUpdateCategory(name: String) {
        viewModelScope.launch {
            val result = createListCategoryUseCase(name = name)
            if (result.success) {
                updateState {
                    copy(
                        uiState = UiState.Content,
                        available = mutableListOf<Category>().apply {
                            addAll(available)
                            add(result.data!!)
                        }
                    )
                }
            } else {
                Log.e(logTag, "Failed to create category", result.error)
                updateState {
                    copy(
                        uiState = UiState.Content
                    )
                }
            }
        }
    }
}