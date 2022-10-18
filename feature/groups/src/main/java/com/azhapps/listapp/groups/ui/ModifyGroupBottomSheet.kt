package com.azhapps.listapp.groups.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.common.ui.BottomSheetContent
import com.azhapps.listapp.common.ui.DialogButton
import com.azhapps.listapp.common.ui.NameField
import com.azhapps.listapp.common.ui.OverflowRow
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.groups.CreateGroupViewModel
import com.azhapps.listapp.groups.ModifyGroupViewModel
import com.azhapps.listapp.groups.R
import com.azhapps.listapp.groups.model.GroupBottomSheetAction
import com.azhapps.listapp.groups.model.GroupBottomSheetState
import com.azhapps.listapp.groups.navigation.CreateGroup
import com.azhapps.listapp.groups.navigation.ModifyGroup
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.dialog.BottomSheetDestination

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(ModifyGroup::class)
fun BottomSheetDestination.ModifyGroupBottomSheet() {
    val viewModel = viewModel<ModifyGroupViewModel>()
    val state = viewModel.collectAsState()

    BottomSheetContent {
        GroupBottomSheetWrapper(
            title = stringResource(id = R.string.groups_bottom_sheet_title),
            state = state,
            actor = viewModel::dispatch,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(CreateGroup::class)
fun BottomSheetDestination.CreateGroupBottomSheet() {
    val viewModel = viewModel<CreateGroupViewModel>()
    val state = viewModel.collectAsState()

    BottomSheetContent {
        GroupBottomSheetWrapper(
            title = stringResource(id = R.string.groups_create_sheet_title),
            state = state,
            actor = viewModel::dispatch,
        )
    }
}

@Composable
fun GroupBottomSheetWrapper(
    title: String,
    state: GroupBottomSheetState,
    actor: (GroupBottomSheetAction) -> Unit,
) {
    GroupBottomSheetContent(
        title = title,
        currentName = state.currentName,
        currentMembers = state.currentMembers,
        actor = actor,
    )

    if (state.showConfirmRemoveRememberDialog) {
        ConfirmRemoveMemberDialog(
            user = state.currentlySelectedUser!!,
            groupName = state.currentName,
            actor = actor,
        )
    }
}

@Composable
fun GroupBottomSheetContent(
    title: String,
    currentName: String,
    currentMembers: List<User>,
    actor: (GroupBottomSheetAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(ListAppTheme.defaultSpacing)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = Typography.h6
        )

        NameField(
            name = currentName,
            onNameChange = {
                actor(GroupBottomSheetAction.UpdateName(it))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            style = Typography.subtitle1,
            text = stringResource(id = R.string.groups_bottom_sheet_label_users),
        )

        OverflowRow(modifier = Modifier.fillMaxWidth()) {
            currentMembers.forEach {
                MemberItem(user = it, actor = actor)
            }
            AddMemberItem(actor = actor)
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                actor(GroupBottomSheetAction.Finalize)
            },
            enabled = currentName.isNotBlank()
        ) {
            Text(text = stringResource(id = R.string.groups_bottom_button_save))
        }
    }
}

@Composable
private fun MemberItem(
    user: User,
    actor: (GroupBottomSheetAction) -> Unit,
) {
    ClickablePill(onClick = {
        actor(GroupBottomSheetAction.ConfirmRemoveMember(user))
    }) {
        Text(
            modifier = Modifier.padding(end = 8.dp),
            text = user.username,
            color = MaterialTheme.colors.onSecondary,
        )

        Icon(
            imageVector = Icons.Filled.Remove,
            contentDescription = stringResource(id = R.string.groups_remove_member),
            tint = MaterialTheme.colors.onSecondary,
        )
    }
}


@Composable
private fun AddMemberItem(
    actor: (GroupBottomSheetAction) -> Unit
) {
    ClickablePill(onClick = {
        actor(GroupBottomSheetAction.FindMember)
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.groups_add_member),
            tint = MaterialTheme.colors.onSecondary
        )
    }
}

@Composable
private fun ClickablePill(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    Box(Modifier.padding(4.dp)) {
        Row(
            modifier = Modifier
                .height(24.dp)
                .background(MaterialTheme.colors.secondary, RoundedCornerShape(8.dp))
                .clickable {
                    onClick()
                }
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
private fun ConfirmRemoveMemberDialog(
    user: User,
    groupName: String,
    actor: (GroupBottomSheetAction) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            actor(GroupBottomSheetAction.DismissDialog)
        },
        confirmButton = {
            DialogButton(
                action = {
                    actor(GroupBottomSheetAction.RemoveMember(user))
                },
                text = stringResource(id = R.string.groups_bottom_confirm_button)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.groups_bottom_confirm_title))
        },
        text = {
            Text(text = stringResource(id = R.string.groups_bottom_confirm_remove_member_text, user.username, groupName))
        }
    )
}