package com.azhapps.listapp.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.ui.theme.ListAppTheme

@Composable
fun <T> FindSelectView(
    itemList: List<T>,
    onItemSelected: (T) -> Unit,
    onDisplayPrimary: (T) -> String,
    onDisplaySecondary: (T) -> String,
    searchFilter: String,
    onSearchFilterChanged: (String) -> Unit,
    title: String,
) {
    ThemedScaffold(
        topBar = {
            TopBar(
                title = title,
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            FindSelectContent(
                itemList = itemList,
                onItemSelected = onItemSelected,
                onDisplayPrimary = onDisplayPrimary,
                onDisplaySecondary = onDisplaySecondary,
                searchFilter = searchFilter,
                onSearchFilterChanged = onSearchFilterChanged,
            )
        }
    }
}

@Composable
private fun <T> FindSelectContent(
    itemList: List<T>,
    onItemSelected: (T) -> Unit,
    onDisplayPrimary: (T) -> String,
    onDisplaySecondary: (T) -> String,
    searchFilter: String,
    onSearchFilterChanged: (String) -> Unit,
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
                searchHint = stringResource(id = R.string.find_select_hint),
                onValueChange = {
                    onSearchFilterChanged(it)
                },
                onClear = {
                    onSearchFilterChanged("")
                }
            )
        }


        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                item {
                    Divider()
                }
                itemList.forEach {
                    item {
                        FindSelectRow(
                            item = it,
                            onItemSelected = onItemSelected,
                            onDisplayPrimary = onDisplayPrimary,
                            onDisplaySecondary = onDisplaySecondary,
                        )
                        Divider()
                    }
                }
            }
        )
    }
}

@Composable
private fun <T> FindSelectRow(
    item: T,
    onItemSelected: (T) -> Unit,
    onDisplayPrimary: (T) -> String,
    onDisplaySecondary: (T) -> String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = ListAppTheme.defaultSpacing)
            .clickable {
                onItemSelected(item)
            }
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = onDisplayPrimary(item)
        )
        Text(text = onDisplaySecondary(item))
    }
}