@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.view.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.ErrorMarker
import com.azhapps.listapp.common.ui.ThemedScaffold
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.ui.Header
import com.azhapps.listapp.lists.view.ViewListViewModel
import com.azhapps.listapp.lists.view.model.ItemCategoryState
import com.azhapps.listapp.lists.view.model.ListItemState
import com.azhapps.listapp.lists.view.model.ViewListAction
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ViewListScreen(viewModel: ViewListViewModel) {
    val navigationHandle = navigationHandle<ViewList>()
    val state = viewModel.collectAsState()

    ThemedScaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.lists_view_title),
                backAction = {
                    navigationHandle.close()
                },
                showBackArrow = true
            ) {
                IconButton(onClick = {
                    viewModel.dispatch(ViewListAction.CreateItem(null))
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.lists_view_add_new),
                    )
                }
            }
        }
    ) {
        val pullRefreshState = rememberPullRefreshState(state.refreshing, { viewModel.dispatch(ViewListAction.RefreshList) })

        Box(
            Modifier
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            ViewListContent(
                listTitle = state.listTitle,
                listCategory = state.listCategory,
                itemStates = state.itemStates,
                actor = viewModel::dispatch,
            )
            PullRefreshIndicator(state.refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun ViewListContent(
    listTitle: String,
    listCategory: String,
    itemStates: Map<String, ItemCategoryState>,
    actor: (ViewListAction) -> Unit,
) {
    Column(
        Modifier
            .background(ListAppTheme.alternateBackgroundColor)
            .fillMaxSize()
            .padding(top = ListAppTheme.defaultSpacing, start = ListAppTheme.defaultSpacing, end = ListAppTheme.defaultSpacing)
    ) {
        NameAndCategory(name = listTitle, category = listCategory)

        LazyColumn(
            modifier = Modifier.padding(top = ListAppTheme.defaultSpacing),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                itemStates.forEach { entry ->
                    itemCategoryCard(
                        entry.key,
                        entry.value,
                        actor
                    )
                }
                if (itemStates.isEmpty()) {
                    item {
                        AddItemButton(actor)
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(ListAppTheme.defaultSpacing))
                }
            }
        )
    }
}

private fun LazyListScope.itemCategoryCard(
    categoryName: String,
    itemCategoryState: ItemCategoryState,
    actor: (ViewListAction) -> Unit,
) {
    item {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 2.dp),
            elevation = 2.dp,
        ) {
            Column(
                modifier = Modifier.padding(all = ListAppTheme.defaultSpacing),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            actor(ViewListAction.ToggleCollapsed(itemCategoryState.category))
                        }
                ) {
                    Header(
                        modifier = Modifier.weight(1F),
                        header = categoryName,
                    )

                    DropDownIcon(dropped = itemCategoryState.collapsed)
                }

                if (!itemCategoryState.collapsed) {
                    itemCategoryState.items.forEachIndexed { position, state ->
                        if (position != 0) {
                            Divider()
                        }
                        ListItem(
                            state = state,
                            actor = actor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NameAndCategory(
    name: String,
    category: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1F),
            style = Typography.h5,
            text = name
        )

        Text(
            style = Typography.subtitle1,
            text = category
        )
    }
}

@Composable
private fun AddItemButton(
    actor: (ViewListAction) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 2.dp),
        elevation = 2.dp,
    ) {
        Column(
            modifier = Modifier.padding(all = ListAppTheme.defaultSpacing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.lists_view_add_new))

            IconButton(
                onClick = {
                    actor(ViewListAction.CreateItem(null))
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = stringResource(id = R.string.lists_view_add_new)
                )
            }
        }
    }

}

@Composable
private fun ListItem(
    state: ListItemState,
    actor: (ViewListAction) -> Unit,
) {
    Row(
        Modifier
            .combinedClickable(onClick = {
                actor(ViewListAction.ToggleComplete(state.item))
            }, onLongClick = {
                actor(ViewListAction.ModifyItem(state.item))
            })
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = state.item.itemText,
            textDecoration = if (state.item.completed) TextDecoration.LineThrough else TextDecoration.None,
        )

        when (state.uiState) {
            UiState.Content -> {
                //Nothing extra required
            }
            is UiState.Error -> ErrorMarker()
            UiState.Loading -> {
                Box(modifier = Modifier.padding(start = 4.dp, end = 4.dp)) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}
