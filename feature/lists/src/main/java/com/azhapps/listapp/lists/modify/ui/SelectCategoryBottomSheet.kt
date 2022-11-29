package com.azhapps.listapp.lists.modify.ui

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.BottomSheetContent
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.FindSelectView
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.modify.SelectCategoryViewModel
import com.azhapps.listapp.lists.modify.model.SelectCategoryAction
import com.azhapps.listapp.lists.navigation.SelectCategory
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.dialog.BottomSheetDestination
import dev.enro.core.compose.navigationHandle

@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalComposableDestination
@NavigationDestination(SelectCategory::class)
fun BottomSheetDestination.SelectCategoryBottomSheet() {
    val viewModel = viewModel<SelectCategoryViewModel>()
    val state = viewModel.collectAsState()
    val navigationHandle = navigationHandle()

    BottomSheetContent {
        when (state.uiState) {
            UiState.Content -> FindSelectView(
                title = stringResource(id = R.string.lists_label_category),
                itemList = state.available.filter { it.name.contains(state.filter) },
                onItemSelected = { viewModel.dispatch(SelectCategoryAction.Select(it)) },
                onDisplayPrimary = { it.name },
                onDisplaySecondary = { "" },
                searchFilter = state.filter,
                onSearchFilterChanged = { viewModel.dispatch(SelectCategoryAction.Filter(it)) },
            )
            is UiState.Error -> ErrorPage(retryAction = {
                viewModel.dispatch(SelectCategoryAction.LoadCategories)
            }, cancelAction = {
                navigationHandle.close()
            })
            UiState.Loading -> LoadingPage()
        }
    }
}
