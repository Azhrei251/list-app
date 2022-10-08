package com.azhapps.listapp.lists.view.ui

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.azhapps.listapp.lists.R

@Composable
fun DropDownIcon(dropped: Boolean) {
    Icon(
        imageVector = if (dropped) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropUp,
        contentDescription = stringResource(id = if (dropped) R.string.lists_drop_down_collapsed else R.string.lists_drop_down_expanded)
    )
}