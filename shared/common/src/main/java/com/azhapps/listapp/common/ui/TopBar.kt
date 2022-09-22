package com.azhapps.listapp.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.ui.theme.Typography

@Composable
fun TopBar(
    title: String,
    backAction: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = Typography.h3
            )
        },
        navigationIcon = {
            IconButton(onClick = backAction) {
                Image(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = stringResource(R.string.btn_back)
                )
            }
        },
        actions = actions
    )
}
