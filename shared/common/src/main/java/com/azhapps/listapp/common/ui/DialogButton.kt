package com.azhapps.listapp.common.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.azhapps.listapp.common.ui.theme.ListAppTheme

@Composable
fun DialogButton(
    action: () -> Unit,
    text: String,
) {
    Text(
        modifier = Modifier
            .padding(all = ListAppTheme.defaultSpacing)
            .clickable {
                action()
            },
        color = MaterialTheme.colors.primary,
        text = text
    )
}