@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.azhapps.listapp.common.ui.theme.Typography

fun LazyGridScope.lazyHeader(header: String) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Header(header = header)
    }
}

fun LazyListScope.lazyHeader(header: String) {
    stickyHeader {
        Header(header = header)
    }
}

@Composable
private fun Header(header: String) {
    Text(
        text = header.uppercase(),
        style = Typography.subtitle2,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}