@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.view.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.ui.lazyHeader
import com.azhapps.listapp.lists.view.ViewListViewModel
import com.azhapps.listapp.lists.view.model.ListItemState
import com.azhapps.listapp.lists.view.model.ViewListAction
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@Composable
@ExperimentalComposableDestination
@NavigationDestination(ViewList::class)
fun ViewListScreen() {
    val viewModel = viewModel<ViewListViewModel>()
    val state = viewModel.collectAsState()

    ViewListContent(
        title = state.title,
        category = state.category,
        itemStates = state.itemStates,
        actor = viewModel::dispatch,
    )
}

@Composable
fun ViewListContent(
    title: String,
    category: String,
    itemStates: Map<String, List<ListItemState>>,
    actor: (ViewListAction) -> Unit,
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = ListAppTheme.defaultSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(1F),
                style = Typography.h5,
                text = title
            )

            Text(
                style = Typography.subtitle1,
                text = category
            )
        }

        LazyColumn(content = {
            itemStates.forEach { entry ->
                lazyHeader(entry.key)
                entry.value.forEach { itemState ->
                    listItem(
                        state = itemState,
                        actor = actor,
                    )
                }
                item {
                    IconButton(onClick = {
                        actor(ViewListAction.CreateItem(entry.value.firstOrNull()?.item?.category))
                    }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add Item")
                    }
                }
            }
            if (itemStates.isEmpty()) {
                item {
                    IconButton(onClick = {
                        actor(ViewListAction.CreateItem(null))
                    }) {
                        Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add Item")
                    }
                }
            }
        })
    }
}

private fun LazyListScope.listItem(
    state: ListItemState,
    actor: (ViewListAction) -> Unit,
) {
    item {
        Row(
            Modifier
                .combinedClickable(onClick = {
                    actor(ViewListAction.ToggleComplete(state.item))
                }, onLongClick = {
                    actor(ViewListAction.ModifyItem(state.item))
                })
                .fillMaxWidth()
                .padding(ListAppTheme.defaultSpacing)
                .height(ROW_HEIGHT),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = state.item.itemText,
                textDecoration = if (state.item.completed) TextDecoration.LineThrough else TextDecoration.None,
            )
        }
    }
}

private val ROW_HEIGHT = 16.dp