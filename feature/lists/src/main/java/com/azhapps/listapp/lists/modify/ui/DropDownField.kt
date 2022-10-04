@file:OptIn(ExperimentalMaterialApi::class)

package com.azhapps.listapp.lists.modify.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun DropDownField(
    currentText: String,
    availableOptions: List<String>,
    onTextChanged: (String) -> Unit,
    isLoading: Boolean,
    label: String,
    enabled: Boolean = true,
    allowNew: Boolean = true,
) {
    if (isLoading) {
        LoadingDropDownTextField(
            currentText = currentText,
            onTextChanged = onTextChanged,
            label = label,
        )
    } else if (availableOptions.isEmpty()) {
        DropDownTextField(
            currentText = currentText,
            onTextChanged = onTextChanged,
            label = label,
            allowNew = allowNew,
            enabled = enabled,
        )
    } else {
        DropDownTextField(
            currentText = currentText,
            availableOptions = availableOptions,
            onTextChanged = onTextChanged,
            label = label,
            allowNew = allowNew,
            enabled = enabled,
        )
    }
}

@Composable
private fun LoadingDropDownTextField(
    currentText: String,
    onTextChanged: (String) -> Unit,
    label: String,
) {
    DropDownTextField(
        enabled = false,
        currentText = currentText,
        onTextChanged = onTextChanged,
        label = label
    ) {
        Image(
            imageVector = Icons.Filled.Downloading,
            contentDescription = "Loading"
        )
    }
}

@Composable
private fun DropDownTextField(
    currentText: String,
    availableOptions: List<String>,
    onTextChanged: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    allowNew: Boolean = true,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        DropDownTextField(
            currentText = currentText,
            onTextChanged = onTextChanged,
            label = label,
            enabled = enabled,
            allowNew = allowNew,
        ) {
            ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = expanded
            )
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            availableOptions.forEach {
                DropdownMenuItem(onClick = {
                    expanded = false
                    onTextChanged(it)
                }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
private fun DropDownTextField(
    currentText: String,
    onTextChanged: (String) -> Unit,
    enabled: Boolean = true,
    allowNew: Boolean = true,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = currentText,
        onValueChange = {
            onTextChanged(it)
        },
        label = {
            Text(text = label)
        },
        singleLine = true,
        trailingIcon = trailingIcon,
        colors = ExposedDropdownMenuDefaults.textFieldColors(),
        enabled = enabled && allowNew,
    )
}
