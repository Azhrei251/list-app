package com.azhapps.listapp.groups.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.ui.DialogButton
import com.azhapps.listapp.common.ui.ErrorMarker
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.groups.GroupsViewModel
import com.azhapps.listapp.groups.R
import com.azhapps.listapp.groups.model.GroupItemState
import com.azhapps.listapp.groups.model.GroupsAction
import com.azhapps.listapp.navigation.Groups
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@Composable
@ExperimentalComposableDestination
@NavigationDestination(Groups::class)
fun GroupsScreen() {
    val viewModel = viewModel<GroupsViewModel>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.groups_title),
            ) {
                IconButton(onClick = {
                    viewModel.dispatch(GroupsAction.CreateGroup)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.groups_add_new),
                    )
                }
            }
        }
    ) {
        Box(Modifier.padding(it)) {
            GroupsPage(
                uiState = state.uiState,
                groupItemStates = state.groupItemStates,
                showConfirmDeleteDialog = state.showConfirmDeleteGroupDialog,
                selectedGroup = state.selectedGroup,
                actor = viewModel::dispatch,
            )
        }
    }
}

@Composable
private fun GroupsPage(
    uiState: UiState,
    groupItemStates: List<GroupItemState>,
    showConfirmDeleteDialog: Boolean,
    selectedGroup: Group?,
    actor: (GroupsAction) -> Unit,
) {
    when (uiState) {
        UiState.Loading -> LoadingPage()
        is UiState.Error -> ErrorPage(
            errorMessage = stringResource(id = R.string.groups_selection_error_message),
            errorTitle = stringResource(id = R.string.groups_selection_error_title),
            retryAction = {
                actor(GroupsAction.GetGroups)
            }, cancelAction = {
                //TODO
            })
        UiState.Content -> {
            GroupsContent(
                groupItemStates = groupItemStates,
                actor = actor
            )
            if (showConfirmDeleteDialog) {
                ConfirmDeleteGroupDialog(actor = actor, group = selectedGroup!!)
            }
        }
    }
}

@Composable
private fun ConfirmDeleteGroupDialog(
    group: Group,
    actor: (GroupsAction) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            actor(GroupsAction.HideConfirmDeleteDialog)
        },
        confirmButton = {
            DialogButton(
                action = {
                    actor(GroupsAction.DeleteGroup(group))
                },
                text = stringResource(id = R.string.groups_bottom_confirm_button)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.groups_bottom_confirm_title))
        },
        text = {
            Text(text = stringResource(id = R.string.groups_bottom_confirm_remove_group_text, group.name))
        }
    )
}

@Composable
private fun GroupsContent(
    groupItemStates: List<GroupItemState>,
    actor: (GroupsAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ListAppTheme.alternateBackgroundColor)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing),
        content = {
            groupItemStates.forEach {
                item {
                    GroupCard(
                        itemState = it,
                        actor = actor
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(ListAppTheme.defaultSpacing))
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GroupCard(
    itemState: GroupItemState,
    actor: (GroupsAction) -> Unit,
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    if (itemState.editable) {
                        actor(GroupsAction.EditGroup(itemState.group))
                    } else {
                        Toast
                            .makeText(context, context.getString(R.string.groups_unowned), Toast.LENGTH_LONG)
                            .show()
                    }
                }, onLongClick = {
                    if (itemState.editable) {
                        actor(GroupsAction.ShowConfirmDeleteDialog(itemState.group))
                    }
                }
            ),
        elevation = 2.dp,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(end = ListAppTheme.defaultSpacing)
                        .weight(1F),
                    text = itemState.group.name,
                    style = Typography.h6
                )

                when (itemState.uiState) {
                    UiState.Content -> {
                        //Do mothing
                    }
                    is UiState.Error -> ErrorMarker()
                    UiState.Loading -> CircularProgressIndicator()
                }
            }

            Text(text = itemState.group.users.joinToString { it.username })
        }
    }
}
