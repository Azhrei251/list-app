package com.azhapps.listapp.groups.find.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.FindSelectView
import com.azhapps.listapp.common.ui.LoadingPage
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
    ListAppTheme {
        when (state.uiState) {
            UiState.Content -> FindSelectView(
                title = stringResource(id = R.string.groups_find_user_title),
                itemList = state.availableUsers,
                onItemSelected = { viewModel.dispatch(FindUserAction.Select(it)) },
                onDisplayPrimary = { it.username },
                onDisplaySecondary = { it.email },
                searchFilter = state.searchFilter,
                onSearchFilterChanged = { viewModel.dispatch(FindUserAction.SearchUsers(it)) },
            )
            is UiState.Error -> ErrorPage(retryAction = {
                viewModel.dispatch(FindUserAction.SearchUsers())
            }, cancelAction = {
                navigationHandle.close()
            })
            UiState.Loading -> LoadingPage()
        }
    }
}
