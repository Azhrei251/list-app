package com.azhapps.listapp.lists.modify

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.modify.ui.ModifyScaffold
import com.azhapps.listapp.lists.navigation.ModifyItem
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(ModifyItem::class)
class ModifyItemActivity : BaseActivity() {

    private val viewModel by enroViewModels<ModifyItemViewModel>()

    @Composable
    override fun InitialContent() {
        val state = viewModel.collectAsState()
        ModifyScaffold(
            title = stringResource(
                id = if (state.isCreate)
                    R.string.lists_create_item_sheet_title
                else
                    R.string.lists_edit_item_sheet_title
            ),
            actor = viewModel::dispatch,
            isCategoriesLoading = state.categoryUiState == UiState.Loading,
            isGroupsLoading = state.groupUiState == UiState.Loading,
            availableCategories = state.availableCategories,
            availableGroups = state.availableGroups,
            currentCategoryName = state.currentCategoryName,
            currentListName = state.currentName,
            currentGroupName = state.currentGroupName,
            editable = state.editable,
            canDelete = state.canDelete,
        )
    }
}