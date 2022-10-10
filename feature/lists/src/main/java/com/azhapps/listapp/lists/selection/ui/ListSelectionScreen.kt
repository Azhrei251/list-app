@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.selection.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.account.toDisplayDate
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.navigation.ListSelection
import com.azhapps.listapp.lists.selection.ListSelectionViewModel
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import com.azhapps.listapp.lists.selection.model.ListSelectionItemState
import com.azhapps.listapp.lists.ui.lazyHeader
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(ListSelection::class)
fun ListSelectionScreen() {
    val navigationHandle = navigationHandle()
    val viewModel = viewModel<ListSelectionViewModel>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.lists_selection_title),
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            when (state.uiState) {
                UiState.Content -> ListSelectionContent(
                    actor = viewModel::dispatch,
                    informativeListMap = state.informativeListMap
                )

                is UiState.Error -> ErrorPage(
                    errorMessage = stringResource(id = R.string.lists_selection_error_message),
                    errorTitle = stringResource(id = R.string.lists_selection_error_title),
                    retryAction = { viewModel.dispatch(ListSelectionAction.GetAllLists) },
                    cancelAction = { navigationHandle.close() }
                )

                UiState.Loading -> LoadingPage()
            }
        }
    }
}

@Composable
fun ListSelectionContent(
    actor: (ListSelectionAction) -> Unit,
    informativeListMap: Map<String, List<ListSelectionItemState>>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .background(ListAppTheme.secondaryBackgroundColor)
            .fillMaxSize()
            .padding(
                top = ListAppTheme.defaultSpacing,
                start = ListAppTheme.defaultSpacing,
                end = ListAppTheme.defaultSpacing
            ),
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing),
        verticalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing),
        content = {
            informativeListMap.forEach {
                lazyHeader(
                    it.key
                )

                it.value.forEach {
                    item {
                        InformativeListCard(
                            itemState = it,
                            actor = actor
                        )
                    }
                }
                item {
                    AddListCard(
                        actor = actor,
                        category = it.value.first().informativeList.category
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    )
}

@Composable
private fun ListCard(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { onLongClick?.invoke() }
            )
            .height(84.dp),
        elevation = 2.dp,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(all = 4.dp)
        ) {
            content()
        }
    }
}

@Composable
fun InformativeListCard(
    itemState: ListSelectionItemState,
    actor: (ListSelectionAction) -> Unit
) {
    ListCard(
        onClick = {
            actor(ListSelectionAction.ShowList(itemState.informativeList))
        }, onLongClick = {
            actor(ListSelectionAction.EditList(itemState.informativeList))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
        ) {
            Text(
                modifier = Modifier.weight(1F),
                text = itemState.informativeList.name,
                style = Typography.h6
            )
            Text(
                text = itemState.informativeList.date.toDisplayDate(),
                style = Typography.subtitle1
            )
            Text(text = "${itemState.informativeList.items.size} items")
        }

        Column(Modifier.fillMaxHeight()) {
            if (itemState.informativeList.group != null && !itemState.informativeList.isOwnedBySelf()) {
                Image(
                    imageVector = Icons.Filled.Group,
                    contentDescription = "From a group"
                )
            }

            if (itemState.uiState == UiState.Loading) {
                Spacer(modifier = Modifier.weight(1F))
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun AddListCard(
    category: Category?,
    actor: (ListSelectionAction) -> Unit
) {
    ListCard(
        onClick = {
            actor(ListSelectionAction.CreateList(category))
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ListAppTheme.defaultSpacing, Alignment.CenterVertically)
        ) {
            Text(text = stringResource(id = R.string.lists_selection_add_new))

            Image(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add new list",
            )
        }
    }
}
