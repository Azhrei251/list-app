package com.azhapps.listapp.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.BuildConfig
import com.azhapps.listapp.R
import com.azhapps.listapp.common.ui.ThemedScaffold
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.main.MainViewModel
import com.azhapps.listapp.main.model.MainAction
import com.azhapps.listapp.main.navigation.Welcome
import com.azhapps.listapp.navigation.DeveloperOptions
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle
import dev.enro.core.forward

@ExperimentalComposableDestination
@NavigationDestination(Welcome::class)
@Composable
fun MainScreen() {
    val navigationHandle = navigationHandle<Welcome>()
    val viewModel = viewModel<MainViewModel>()

    ThemedScaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.main_title),
                backAction = {
                    navigationHandle.close()
                },
                showBackArrow = true
            )
        }
    ) {
        Box(
            Modifier
                .padding(it)
                .fillMaxSize()
                .padding(top = 24.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            MainContent(actor = viewModel::dispatch)
        }
        if (BuildConfig.DEBUG) {
            IconButton(onClick = {
                navigationHandle.forward(DeveloperOptions)
            }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(id = R.string.main_button_developer_options)
                )
            }
        }
    }
}

@Composable
fun MainContent(
    actor: (MainAction) -> Unit,
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
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.5F),
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