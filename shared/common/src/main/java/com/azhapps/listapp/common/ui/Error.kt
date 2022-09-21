package com.azhapps.listapp.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.ui.theme.BackgroundColor
import com.azhapps.listapp.common.ui.theme.Typography

@Composable
fun ErrorPage(
    errorMessage: String,
    errorTitle: String,
    retryText: String = stringResource(R.string.btn_retry),
    cancelText: String = stringResource(R.string.btn_cancel),
    retryAction: () -> Unit,
    cancelAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor),
        verticalArrangement = Arrangement.spacedBy(
            12.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_error),
            contentDescription = errorMessage
        )
        Text(
            style = Typography.h2,
            text = errorTitle
        )
        Text(errorMessage)
        Row {
            Button(onClick = cancelAction) {
                Text(cancelText)
            }
            Button(onClick = retryAction) {
                Text(retryText)
            }
        }
    }
}
