package com.azhapps.listapp.lists.selection

import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.ListItem
import com.azhapps.listapp.lists.selection.model.ListSelectionItemState
import com.azhapps.listapp.lists.view.model.ListItemState

const val UNCATEGORIZED = "Uncategorized"

fun List<InformativeList>.mapByListCategory() =
    sortedByDescending {
        it.date
    }.map {
        ListSelectionItemState(it)
    }.groupBy {
        it.informativeList.category?.name ?: UNCATEGORIZED
    }.toSortedMap(categoryNameComparator)

fun List<ListItem>.mapByItemCategory() =
    map {
        ListItemState(it)
    }.groupBy {
        it.item.category?.name ?: UNCATEGORIZED
    }.toSortedMap(categoryNameComparator)

private val categoryNameComparator = compareBy<String>(
    {
        it == UNCATEGORIZED
    },
    {
        it
    },
)