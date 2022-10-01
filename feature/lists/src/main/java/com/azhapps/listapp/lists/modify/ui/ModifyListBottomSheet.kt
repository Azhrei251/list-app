package com.azhapps.listapp.lists.modify.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.modify.ModifyListViewModel
import com.azhapps.listapp.lists.modify.model.ModifyListAction
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.navigation.ModifyList
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.dialog.configureBottomSheet

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(ModifyList::class)
fun BottomSheetDestination.ModifyListBottomSheet() {
    configureBottomSheet(block = {
    })

    val viewModel = viewModel<ModifyListViewModel>()

    val state = viewModel.collectAsState()

    EditListBottomSheetContent(
        actor = viewModel::dispatch,
        isCategoriesLoading = state.categoryUiState == UiState.Loading,
        isGroupsLoading = state.groupUiState == UiState.Loading,
        availableCategories = state.availableCategories,
        availableGroups = state.availableGroups,
        currentCategoryName = state.currentCategoryName,
        currentListName = state.currentListName,
        currentGroupName = state.currentGroupName,
        editable = state.editable,
    )
}

@Composable
fun EditListBottomSheetContent(
    actor: (ModifyListAction) -> Unit,
    isCategoriesLoading: Boolean,
    isGroupsLoading: Boolean,
    availableCategories: List<Category>,
    availableGroups: List<Group>,
    currentCategoryName: String,
    currentListName: String,
    currentGroupName: String,
    editable: Boolean,
) {
    Column(
        modifier = Modifier
            .padding(ListAppTheme.defaultSpacing)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing)
    ) {
        Text(
            text = stringResource(id = R.string.lists_edit_list_sheet_title),
            style = Typography.h6
        )

        NameField(
            name = currentListName,
            onNameChange = {
                actor(ModifyListAction.UpdateListName(it))
            },
            enabled = editable
        )

        DropDownField(
            currentText = currentCategoryName,
            availableOptions = availableCategories.map { it.name },
            onTextChanged = {
                actor(ModifyListAction.UpdateCategory(it))
            },
            isLoading = isCategoriesLoading,
            label = stringResource(id = R.string.lists_label_category),
            enabled = editable,
        )

        if (availableGroups.isNotEmpty()) {
            DropDownField(
                currentText = currentGroupName,
                availableOptions = availableGroups.map { it.name },
                onTextChanged = {
                    actor(ModifyListAction.UpdateGroup(it))
                },
                isLoading = isGroupsLoading,
                label = stringResource(id = R.string.lists_label_group),
                allowNew = false,
                enabled = editable,
            )
        }

        Button(
            onClick = { actor(ModifyListAction.Finalize) },
            enabled = editable,
        ) {
            Text(text = stringResource(id = R.string.lists_button_save))
        }
    }
}
