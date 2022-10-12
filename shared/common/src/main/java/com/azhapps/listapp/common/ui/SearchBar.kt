package com.azhapps.listapp.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.ui.theme.ListAppTheme

@Composable
fun SearchBar(
    searchText: String,
    searchHint: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(color = ListAppTheme.secondaryBackgroundColor, shape = RoundedCornerShape(16.dp))
    ) {
        TextField(
            modifier = modifier,
            value = searchText,
            onValueChange = onValueChange,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = searchHint)
            },
            label = {
                Text(searchHint)
            },
            trailingIcon = {
                if (searchText.isNotBlank()) {
                    IconButton(onClick = { onClear() }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = stringResource(id = R.string.label_clear)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}