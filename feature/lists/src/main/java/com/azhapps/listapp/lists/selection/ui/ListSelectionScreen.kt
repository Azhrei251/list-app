package com.azhapps.listapp.lists.selection.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.lists.navigation.ListsInternalNavigationKeys
import com.azhapps.listapp.lists.selection.ListSelectionViewModel
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(ListsInternalNavigationKeys.ListSelection::class)
fun ListSelectionScreen() {
    //val navigationHandle = navigationHandle<ListsInternalNavigationKeys.ListSelection>()
    val viewModel = viewModel<ListSelectionViewModel>()
    val state = viewModel.collectAsState()

    viewModel.dispatch(ListSelectionAction.GetAllLists)

    LazyColumn(content ={
        state.personalLists.forEach {
            item {
                Text(it.name)
            }
        }
    })
}