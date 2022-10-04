package com.azhapps.listapp.lists.view.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.ui.lazyHeader
import com.azhapps.listapp.lists.view.ViewListViewModel
import com.azhapps.listapp.lists.view.model.ListItemState
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
        itemStates = state.itemStates
    )
}

@Composable
fun ViewListContent(
    title: String,
    category: String,
    itemStates: Map<String, List<ListItemState>>,
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
            itemStates.forEach {
                lazyHeader(it.key)
                it.value.forEach { itemState ->
                    listItem(itemState)
                }
            }
        })
    }

}

private fun LazyListScope.listItem(listItemState: ListItemState) {
    item {
        Box(

        ) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(ListAppTheme.defaultSpacing)) {
                Text(text = listItemState.item.itemText)
            }
        }
    }
}