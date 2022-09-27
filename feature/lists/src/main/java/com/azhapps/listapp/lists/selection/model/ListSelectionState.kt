package com.azhapps.listapp.lists.selection.model

import com.azhapps.listapp.lists.model.InformativeList

data class ListSelectionState(
    val personalLists: List<InformativeList> = emptyList(),
    val groupLists: Map<String, List<InformativeList>> = emptyMap()
)
