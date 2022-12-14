package com.azhapps.listapp.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.ui.theme.Typography

//TODO Add logout functionality for refresh fail
@Composable
fun ErrorPage(
    errorMessage: String = stringResource(id = R.string.error_default_message),
    errorTitle: String = stringResource(id = R.string.error_default_title),
    retryText: String = stringResource(R.string.btn_retry),
    cancelText: String = stringResource(R.string.btn_cancel),
    retryAction: () -> Unit,
    cancelAction: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 12.dp, end = 12.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(
            8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier.fillMaxWidth(0.2f),
            imageVector = Icons.Filled.Error,
            contentDescription = errorMessage,
            tint = Color.Red,
        )
        Text(
            style = Typography.h4,
            text = errorTitle
        )
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.weight(1F))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(onClick = cancelAction) {
                Text(cancelText)
            }
            Button(onClick = retryAction) {
                Text(retryText)
            }
        }
    }
}
