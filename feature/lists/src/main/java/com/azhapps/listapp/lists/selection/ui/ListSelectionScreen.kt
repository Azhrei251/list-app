@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.selection.ui

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.account.toDisplayDate
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.ErrorPage
import com.azhapps.listapp.common.ui.LoadingPage
import com.azhapps.listapp.common.ui.ThemedScaffold
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.selection.ListSelectionViewModel
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import com.azhapps.listapp.lists.selection.model.ListSelectionItemState
import com.azhapps.listapp.lists.ui.lazyHeader
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListSelectionScreen() {
    val navigationHandle = navigationHandle()
    val viewModel = viewModel<ListSelectionViewModel>()
    val state = viewModel.collectAsState()

    ThemedScaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.lists_selection_title),
            ) {
                IconButton(onClick = {
                    viewModel.dispatch(ListSelectionAction.CreateList(null))
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.lists_view_add_new),
                    )
                }
            }
        }
    ) {
        val pullRefreshState = rememberPullRefreshState(state.refreshing, { viewModel.dispatch(ListSelectionAction.GetAllLists(true)) })
        Box(
            Modifier
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            when (state.uiState) {
                UiState.Content -> if (state.informativeListMap.isNotEmpty()) {
                    ListSelectionContent(
                        actor = viewModel::dispatch,
                        informativeListMap = state.informativeListMap
                    )
                } else {
                    ListSelectionEmptyContent()
                }

                is UiState.Error -> ErrorPage(
                    errorMessage = stringResource(id = R.string.lists_selection_error_message),
                    errorTitle = stringResource(id = R.string.lists_selection_error_title),
                    retryAction = { viewModel.dispatch(ListSelectionAction.GetAllLists(false)) },
                    cancelAction = { navigationHandle.close() }
                )

                UiState.Loading -> LoadingPage()
            }
            PullRefreshIndicator(state.refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
private fun ListSelectionEmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.lists_selection_empty),
                textAlign = TextAlign.Center
            )
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
            .background(ListAppTheme.alternateBackgroundColor)
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
            }
            item(span = {
                GridItemSpan(maxLineSpan)
            }) {
                Spacer(modifier = Modifier.height(ListAppTheme.defaultSpacing))
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
            ),
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
                text = itemState.informativeList.name,
                style = Typography.h6
            )
            Text(
                text = itemState.informativeList.date.toDisplayDate(),
                style = Typography.subtitle1
            )
            Text(text = "${itemState.informativeList.items.count { !it.completed }}/${itemState.informativeList.items.size} items")
        }

        Column(Modifier.fillMaxHeight()) {
            if (itemState.informativeList.group != null && !itemState.informativeList.isOwnedBySelf()) {
                Icon(
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
