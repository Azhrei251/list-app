package com.azhapps.listapp.lists.modify.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.modify.ModifyItemViewModel
import com.azhapps.listapp.lists.modify.ModifyListViewModel
import com.azhapps.listapp.lists.modify.model.ModifyAction
import com.azhapps.listapp.lists.navigation.ModifyItem
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
        currentListName = state.currentName,
        currentGroupName = state.currentGroupName,
        editable = state.editable,
        canDelete = state.canDelete,
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(ModifyItem::class)
fun BottomSheetDestination.ModifyItemBottomSheet() {
    configureBottomSheet(block = {
    })

    val viewModel = viewModel<ModifyItemViewModel>()

    val state = viewModel.collectAsState()

    EditListBottomSheetContent(
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

@Composable
fun EditListBottomSheetContent(
    actor: (ModifyAction) -> Unit,
    isCategoriesLoading: Boolean,
    isGroupsLoading: Boolean,
    availableCategories: List<Category>,
    availableGroups: List<Group>,
    currentCategoryName: String,
    currentListName: String,
    currentGroupName: String,
    editable: Boolean,
    canDelete: Boolean,
) {
    Column(
        modifier = Modifier
            .padding(ListAppTheme.defaultSpacing)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = stringResource(id = R.string.lists_edit_list_sheet_title),
                style = Typography.h6
            )

            if (canDelete) {
                IconButton(
                    enabled = editable,
                    onClick = {
                        actor(ModifyAction.Finalize(true))
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_trash_bin),
                        contentDescription = stringResource(
                            id = R.string.lists_delete
                        ),
                        tint = Color.Unspecified,
                    )
                }
            }
        }

        NameField(
            name = currentListName,
            onNameChange = {
                actor(ModifyAction.UpdateListName(it))
            },
            enabled = editable
        )

        DropDownField(
            currentText = currentCategoryName,
            availableOptions = availableCategories.map { it.name },
            onTextChanged = {
                actor(ModifyAction.UpdateCategory(it))
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
                    actor(ModifyAction.UpdateGroup(it))
                },
                isLoading = isGroupsLoading,
                label = stringResource(id = R.string.lists_label_group),
                allowNew = false,
                enabled = editable,
            )
        }

        Button(
            onClick = { actor(ModifyAction.Finalize()) },
            enabled = editable,
        ) {
            Text(text = stringResource(id = R.string.lists_button_save))
        }
    }
}
