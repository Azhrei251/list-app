package com.azhapps.listapp.groups.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
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
                actor = viewModel::dispatch,
            )
        }
    }
}

@Composable
private fun GroupsPage(
    uiState: UiState,
    groupItemStates: List<GroupItemState>,
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
        }
    }
}

@Composable
private fun GroupsContent(
    groupItemStates: List<GroupItemState>,
    actor: (GroupsAction) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ListAppTheme.secondaryBackgroundColor)
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
        }
    )
}

@Composable
private fun GroupCard(
    itemState: GroupItemState,
    actor: (GroupsAction) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                actor(GroupsAction.EditGroup(itemState.group))
            },
        elevation = 2.dp,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                text = itemState.group.name,
                style = Typography.h6
            )
            Text(text = itemState.group.users.joinToString { it.username })
        }
    }
}
