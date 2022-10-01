package com.azhapps.listapp.lists.edit.ui

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
import com.azhapps.listapp.lists.edit.EditListViewModel
import com.azhapps.listapp.lists.edit.model.EditListAction
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.navigation.EditList
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.dialog.configureBottomSheet

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(EditList::class)
fun BottomSheetDestination.EditListBottomSheet() {
    configureBottomSheet(block = {
    })

    val viewModel = viewModel<EditListViewModel>()

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
    actor: (EditListAction) -> Unit,
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
                actor(EditListAction.UpdateListName(it))
            },
            enabled = editable
        )

        DropDownField(
            currentText = currentCategoryName,
            availableOptions = availableCategories.map { it.name },
            onTextChanged = {
                actor(EditListAction.UpdateCategory(it))
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
                    actor(EditListAction.UpdateGroup(it))
                },
                isLoading = isGroupsLoading,
                label = stringResource(id = R.string.lists_label_group),
                allowNew = false,
                enabled = editable,
            )
        }

        Button(
            onClick = { actor(EditListAction.Finalize) },
            enabled = editable,
        ) {
            Text(text = stringResource(id = R.string.lists_button_save))
        }
    }
}
