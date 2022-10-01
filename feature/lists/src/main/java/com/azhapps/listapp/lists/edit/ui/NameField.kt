package com.azhapps.listapp.lists.edit.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.azhapps.listapp.lists.R

@Composable
fun NameField(
    name: String,
    onNameChange: (String) -> Unit,
    enabled: Boolean = true
) {
    TextField(
        value = name,
        onValueChange = onNameChange,
        label = {
            Text(text = stringResource(id = R.string.lists_label_name))
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = enabled,
    )
}