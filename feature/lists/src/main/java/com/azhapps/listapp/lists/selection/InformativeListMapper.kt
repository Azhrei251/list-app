package com.azhapps.listapp.lists.selection

import com.azhapps.listapp.lists.model.InformativeList

private const val UNCATEGORIZED = "Uncategorized"

fun List<InformativeList>.mapByCategory() =
    sortedByDescending {
        it.date
    }.groupBy {
        it.category?.name ?: UNCATEGORIZED
    }.toSortedMap { key1, key2 ->
        when {
            key1 == UNCATEGORIZED -> 1
            key2 == UNCATEGORIZED -> -1
            else -> 0
        }
    }

