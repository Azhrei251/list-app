package com.azhapps.listapp.groups.find.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.common.ui.SearchBar
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.groups.R
import com.azhapps.listapp.groups.find.FindUserViewModel
import com.azhapps.listapp.groups.find.model.FindUserAction
import com.azhapps.listapp.groups.navigation.FindUser
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(FindUser::class)
fun FindUserScreen() {
    val viewModel = viewModel<FindUserViewModel>()
    val state = viewModel.collectAsState()
    val navigationHandle = navigationHandle()
    when (state.uiState) {
        UiState.Content -> FindUserScaffold(
            userList = state.availableUsers,
            actor = viewModel::dispatch,
            searchFilter = state.searchFilter,
        )
        is UiState.Error -> ErrorPage(retryAction = {
            viewModel.dispatch(FindUserAction.SearchUsers())
        }, cancelAction = {
            navigationHandle.close()
        })
        UiState.Loading -> LoadingPage()
    }
}

@Composable
fun FindUserScaffold(
    userList: List<User>,
    searchFilter: String,
    actor: (FindUserAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.groups_find_user_title),
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            FindUserContent(
                userList = userList,
                actor = actor,
                searchFilter = searchFilter,
            )
        }
    }
}

@Composable
fun FindUserContent(
    userList: List<User>,
    searchFilter: String,
    actor: (FindUserAction) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ListAppTheme.defaultSpacing),
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                searchText = searchFilter,
                searchHint = stringResource(id = R.string.groups_find_user_hint),
                onValueChange = {
                    actor(FindUserAction.SearchUsers(it))
                },
                onClear = {
                    actor(FindUserAction.Clear)
                }
            )
        }


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                item {
                    Divider()
                }
                userList.forEach {
                    item {
                        UserRow(user = it, actor = actor)
                        Divider()
                    }
                }
            }
        )
    }
}

@Composable
fun UserRow(
    user: User,
    actor: (FindUserAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = ListAppTheme.defaultSpacing)
            .clickable {
                actor(FindUserAction.Select(user))
            }
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = user.username
        )
        Text(text = user.email)
    }
}