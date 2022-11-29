package com.azhapps.listapp.lists.modify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.ui.DropDownField
import com.azhapps.listapp.common.ui.NameField
import com.azhapps.listapp.common.ui.ThemedScaffold
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.modify.model.ModifyAction
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

//TODO Think about how to add "Create" here
@Composable
fun ModifyScaffold(
    actor: (ModifyAction) -> Unit,
    isGroupsLoading: Boolean,
    availableGroups: List<Group>,
    currentCategoryName: String,
    currentListName: String,
    currentGroupName: String,
    editable: Boolean,
    canDelete: Boolean,
    title: String,
) {
    ThemedScaffold(
        topBar = {
            ModifyTopBar(
                title = title,
                canDelete = canDelete,
                editable = editable,
                actor = actor
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(12.dp)) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { actor(ModifyAction.Finalize()) },
                    enabled = editable,
                ) {
                    Text(text = stringResource(id = R.string.lists_button_save))
                }
            }
        }
    ) {
        ModifyContent(
            actor = actor,
            isGroupsLoading = isGroupsLoading,
            availableGroups = availableGroups,
            currentCategoryName = currentCategoryName,
            currentListName = currentListName,
            currentGroupName = currentGroupName,
            editable = editable,
        )
    }
}

@Composable
private fun ModifyTopBar(
    title: String,
    canDelete: Boolean,
    editable: Boolean,
    actor: (ModifyAction) -> Unit,
) {
    val navigationHandle = navigationHandle()

    TopBar(
        title = title,
        backAction = {
            navigationHandle.close()
        },
        showBackArrow = true,
        actions = {
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
    )
}

@Composable
private fun ModifyContent(
    actor: (ModifyAction) -> Unit,
    isGroupsLoading: Boolean,
    availableGroups: List<Group>,
    currentCategoryName: String,
    currentListName: String,
    currentGroupName: String,
    editable: Boolean,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ListAppTheme.alternateBackgroundColor,
    ) {
        Column(
            modifier = Modifier
                .padding(ListAppTheme.defaultSpacing)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing)
        ) {
            NameField(
                name = currentListName,
                onNameChange = {
                    actor(ModifyAction.UpdateListName(it))
                },
                enabled = editable
            )
            TextField(
                value = currentCategoryName,
                onValueChange = {},
                label = {
                    Text(stringResource(id = R.string.lists_label_category))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (editable) {
                            actor(ModifyAction.SelectCategory)
                        }
                    },
                trailingIcon = {
                    if (currentCategoryName.isNotBlank()) {
                        IconButton(
                            onClick = {
                                actor(ModifyAction.UpdateCategory(null))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(id = R.string.lists_clear)
                            )
                        }
                    }
                },
                singleLine = true,
                enabled = false,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
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
        }
    }
}
