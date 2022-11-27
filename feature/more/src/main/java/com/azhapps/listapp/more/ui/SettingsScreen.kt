package com.azhapps.listapp.more.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.ui.DropDownField
import com.azhapps.listapp.common.ui.ThemedScaffold
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.more.R
import com.azhapps.listapp.more.Settings
import com.azhapps.listapp.more.SettingsViewModel
import com.azhapps.listapp.more.model.SettingsAction
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val state = viewModel.collectAsState()
    val navigationHandle = navigationHandle<Settings>()

    ThemedScaffold(
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
            SettingsContent(
                defaultGroup = state.defaultGroup,
                availableGroups = state.availableGroups,
                actor = viewModel::dispatch
            )
        }
    }
}

@Composable
fun SettingsContent(
    defaultGroup: Group? = null,
    availableGroups: List<Group>,
    actor: (SettingsAction) -> Unit,
) {
    if (availableGroups.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ListAppTheme.defaultSpacing),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(stringResource(id = R.string.settings_default_group_explanation))
            DropDownField(
                currentText = defaultGroup?.name ?: "",
                availableOptions = availableGroups.map { it.name },
                onTextChanged = { name ->
                    actor(SettingsAction.UpdateDefaultGroup(availableGroups.first { it.name == name }))
                },
                isLoading = false,
                label = stringResource(id = R.string.settings_default_group),
                allowNew = false,
            )
        }
    }
}
