package com.azhapps.listapp.more.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.DropDownField
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.more.DeveloperOptionsViewModel
import com.azhapps.listapp.more.R
import com.azhapps.listapp.more.model.DeveloperOptionsAction
import com.azhapps.listapp.navigation.DeveloperOptions
import com.azhapps.listapp.network.model.Environment
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(DeveloperOptions::class)
fun DeveloperOptionsScreen() {
    val navigationHandle = navigationHandle<DeveloperOptions>()
    val viewModel = viewModel<DeveloperOptionsViewModel>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.settings_title),
                backAction = {
                    navigationHandle.close()
                },
                showBackArrow = true
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            DeveloperOptionsContent(
                currentEnvironment = state.currentEnvironment,
                availableEnvironments = state.availableEnvironments,
                actor = viewModel::dispatch
            )
        }
    }
}

@Composable
fun DeveloperOptionsContent(
    currentEnvironment: Environment,
    availableEnvironments: List<Environment>,
    actor: (DeveloperOptionsAction) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(all = ListAppTheme.defaultSpacing)
    ) {
        val context = LocalContext.current
        DropDownField(
            currentText = stringResource(id = currentEnvironment.displayName),
            availableOptions = availableEnvironments.map { stringResource(id = it.displayName) },
            onTextChanged = { selected ->
                actor(DeveloperOptionsAction.UpdateEnvironment(availableEnvironments.first {
                    context.getString(it.displayName) == selected
                }))
            },
            isLoading = false,
            label = stringResource(id = R.string.developer_options_environment),
            enabled = false,
        )
    }
}
