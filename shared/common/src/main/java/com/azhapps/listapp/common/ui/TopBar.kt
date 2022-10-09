package com.azhapps.listapp.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.ui.theme.Typography

@Composable
fun TopBar(
    title: String,
    showBackArrow: Boolean = false,
    backAction: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = Typography.h6
            )
        },
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = backAction) {
                    Image(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.btn_back),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }
        },
        actions = actions
    )
}
