package com.azhapps.listapp.more.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.DialogButton
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.more.About
import com.azhapps.listapp.more.AboutViewModel
import com.azhapps.listapp.more.BuildConfig
import com.azhapps.listapp.more.R
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalComposableDestination
@NavigationDestination(About::class)
fun AboutScreen() {
    val viewModel = viewModel<AboutViewModel>()
    val state = viewModel.collectAsState()

    BottomSheetScaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.about_title),
            )
        },
        sheetContent = {
            PrivacyPolicy(actor = viewModel::dispatch)
        }
    ) {
        Box(Modifier.padding(it)) {
            AboutContent(viewModel::dispatch)
        }
    }
}

@Composable
fun PrivacyPolicy(
    actor: (AboutAction) -> Unit
) {
    Column {
        Text(text = stringResource(id = R.string.about_app_dialog_privacy_title))

        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            Text(text = stringResource(id = R.string.about_app_dialog_privacy_policy))
        }

        DialogButton(
            action = { actor(AboutAction.HidePrivacyPolicy) },
            text = stringResource(id = R.string.about_app_privacy_button_ok)
        )
    }
}

@Composable
fun AboutContent(
    actor: (AboutAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ListAppTheme.defaultSpacing),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AboutRow(
            title = stringResource(
                id = R.string.about_app_version
            ),
            content = BuildConfig.VERSION_NAME
        )
        AboutRow(
            title = stringResource(id = R.string.about_app_privacy_policy),
            content = stringResource(id = R.string.about_app_privacy_button)
        ) {
            IconButton(
                onClick = {
                    actor(AboutAction.ShowPrivacyPolicy)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.OpenInNew,
                    contentDescription = stringResource(id = R.string.about_app_privacy_button),
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
    }
}

@Composable
private fun AboutRow(
    title: String,
    content: String,
    trailingIcon: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
            .padding(all = ListAppTheme.defaultSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1F),
            text = title
        )
        Text(text = content)
        trailingIcon()
    }
    Divider()
}