package com.azhapps.listapp.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.R
import com.azhapps.listapp.main.model.MainAction

@Composable
fun MainScreen(
    actor: (MainAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_welcome),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.padding(8.dp).fillMaxWidth(0.5F),
            )
            Text(
                text = stringResource(id = R.string.main_button_call_to_action),
                textAlign = TextAlign.Center,
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    actor(MainAction.NavigateToLogin)
                }) {
                Text(stringResource(id = R.string.main_button_login))
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    actor(MainAction.NavigateToRegister)
                }) {
                Text(stringResource(id = R.string.main_button_register))
            }
        }
    }
}