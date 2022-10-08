@file:OptIn(ExperimentalFoundationApi::class)

package com.azhapps.listapp.lists.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography

@JvmOverloads //Weirdly needed by compose or it crashes?
fun LazyGridScope.lazyHeader(
    header: String,
    onClick: (String) -> Unit = {},
) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Header(
            header = header,
            onClick = onClick,
        )
    }
}

fun LazyListScope.lazyHeader(
    header: String,
    onClick: (String) -> Unit = {},
) {
    stickyHeader {
        Header(
            header = header,
            onClick = onClick,
        )
    }
}

@Composable
fun Header(
    header: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
) {
    Text(
        modifier = modifier
            .clickable {
                onClick(header)
            }
            .padding(end = ListAppTheme.defaultSpacing),
        text = header.uppercase(),
        style = Typography.subtitle2,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
    )
}