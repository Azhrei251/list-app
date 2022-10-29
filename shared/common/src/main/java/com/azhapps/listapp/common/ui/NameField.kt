package com.azhapps.listapp.common.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.azhapps.listapp.common.R

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
            Text(text = stringResource(id = R.string.label_name))
        },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
    )
}