package com.azhapps.listapp.groups.ui

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.common.ui.NameField
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.groups.CreateGroupViewModel
import com.azhapps.listapp.groups.ModifyGroupViewModel
import com.azhapps.listapp.groups.R
import com.azhapps.listapp.groups.model.GroupBottomSheetAction
import com.azhapps.listapp.groups.navigation.CreateGroup
import com.azhapps.listapp.groups.navigation.ModifyGroup
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.dialog.configureBottomSheet

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(ModifyGroup::class)
fun BottomSheetDestination.ModifyGroupBottomSheet() {
    configureBottomSheet(block = {
    })

    val viewModel = viewModel<ModifyGroupViewModel>()
    val state = viewModel.collectAsState()

    GroupBottomSheetContent(
        title = stringResource(id = R.string.groups_create_sheet_title),
        currentName = state.currentName,
        currentMembers = state.currentMembers,
        actor = viewModel::dispatch,
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
@ExperimentalComposableDestination
@NavigationDestination(CreateGroup::class)
fun BottomSheetDestination.CreateGroupBottomSheet() {
    configureBottomSheet(block = {
    })

    val viewModel = viewModel<CreateGroupViewModel>()
    val state = viewModel.collectAsState()

    GroupBottomSheetContent(
        title = stringResource(id = R.string.groups_bottom_sheet_title),
        currentName = state.currentName,
        currentMembers = state.currentMembers,
        actor = viewModel::dispatch,
    )
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
        currentMembers.forEach {
            MemberRow(user = it, actor = actor)
        }
    }
}

@Composable
fun MemberRow(
    user: User,
    actor: (GroupBottomSheetAction) -> Unit,
) {
    Row(
        modifier = Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = user.username,
        )

        IconButton(onClick = {
            actor(GroupBottomSheetAction.RemoveMember(user.id))
        }) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = stringResource(id = R.string.groups_remove_member)
            )
        }
    }
}