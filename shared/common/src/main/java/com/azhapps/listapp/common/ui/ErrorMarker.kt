package com.azhapps.listapp.common.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.R

@Composable
fun ErrorMarker(
    message: String = stringResource(id = R.string.error_update_failed)
) {
    Text(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp
        ),
        text = message,
        color = Color.Red
    )
    Icon(
        modifier = Modifier.size(24.dp),
        imageVector = Icons.Filled.Error,
        contentDescription = "Error",
        tint = Color.Red
    )
}