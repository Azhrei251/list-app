package com.azhapps.listapp.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.azhapps.listapp.common.R
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.theme.ListAppTheme

@Composable
fun ContentWithDialogs(
    uiState: UiState,
    onErrorDismiss: () -> Unit,
    onErrorButton: () -> Unit,
    errorButton: String = stringResource(id = R.string.error_default_button),
    errorTitle: String = stringResource(id = R.string.error_default_title),
    errorMessage: String = stringResource(id = R.string.error_default_message),
    content: @Composable () -> Unit,
) {
    if (uiState is UiState.Error) {
        AlertDialog(
            onDismissRequest = {
                onErrorDismiss()
            },
            confirmButton = {
                DialogButton(
                    action = {
                        onErrorButton()
                    },
                    text = errorButton
                )
            },
            title = {
                Text(text = errorTitle)
            },
            text = {
                Text(text = errorMessage)
            }
        )
    }
    if (uiState == UiState.Loading) {
        Dialog(onDismissRequest = { /* Do nothing */ }) {
            Box(
                modifier = Modifier
                    .background(
                        color = ListAppTheme.alternateBackgroundColor,
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(28.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
    content()
}