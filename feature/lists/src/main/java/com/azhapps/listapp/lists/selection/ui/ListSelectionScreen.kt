@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.selection.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.account.toDisplayDate
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.R
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.isOwnedBySelf
import com.azhapps.listapp.lists.navigation.ListsInternalNavigationKeys
import com.azhapps.listapp.lists.selection.ListSelectionViewModel
import com.azhapps.listapp.lists.selection.mapByCategory
import com.azhapps.listapp.lists.selection.model.ListSelectionAction
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(ListsInternalNavigationKeys.ListSelection::class)
fun ListSelectionScreen() {
    val navigationHandle = navigationHandle<ListsInternalNavigationKeys.ListSelection>()
    val viewModel = viewModel<ListSelectionViewModel>()
    val state = viewModel.collectAsState()

    viewModel.dispatch(ListSelectionAction.GetAllLists)
    ListSelectionContent(
        actor = viewModel::dispatch,
        personalLists = state.personalLists
    )
}

@Composable
@ExperimentalFoundationApi
fun ListSelectionContent(
    actor: (ListSelectionAction) -> Unit,
    personalLists: List<InformativeList>
) {
    LazyVerticalGrid(
        modifier = Modifier
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
            personalLists.mapByCategory().forEach {
                header(
                    it.key
                )

                it.value.forEach {
                    item {
                        InformativeListCard(
                            informativeList = it,
                            actor = actor
                        )
                    }
                }
                item {
                    AddListCard(
                        actor = actor,
                        category = it.value.first().category
                    )
                }
            }

        }
    )
}

fun LazyGridScope.header(header: String) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Text(
            text = header.uppercase(),
            style = Typography.subtitle2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun ListCard(
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Card(
        modifier = Modifier.combinedClickable(
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
    informativeList: InformativeList,
    actor: (ListSelectionAction) -> Unit
) {
    ListCard(
        onClick = {
            actor(ListSelectionAction.ShowList(informativeList))
        }, onLongClick = {
            actor(ListSelectionAction.EditList(informativeList))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Text(
                text = informativeList.name,
                style = Typography.h6
            )
            Text(
                text = informativeList.date.toDisplayDate(),
                style = Typography.subtitle1
            )
            Text(text = "${informativeList.items.size} items")
        }

        if (informativeList.group != null && !informativeList.isOwnedBySelf()) {
            Spacer(modifier = Modifier.weight(1F))
            Image(
                imageVector = Icons.Filled.Group,
                contentDescription = "From a group"
            )
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
