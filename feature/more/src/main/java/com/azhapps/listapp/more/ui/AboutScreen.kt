package com.azhapps.listapp.more.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.ErrorMarker
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.more.About
import com.azhapps.listapp.more.AboutViewModel
import com.azhapps.listapp.more.BuildConfig
import com.azhapps.listapp.more.R
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@Composable
@ExperimentalComposableDestination
@NavigationDestination(About::class)
fun AboutScreen() {
    val viewModel = viewModel<AboutViewModel>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.about_title),
            )
        },
    ) {
        Box(Modifier.padding(it)) {
            AboutContent(
                userSection = state.userSectionState,
                actor = viewModel::dispatch,
            )
        }
    }
}

@Composable
fun AboutContent(
    userSection: UserSectionState,
    actor: (AboutAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(ListAppTheme.defaultSpacing),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        UserSection(userSection = userSection)
        AboutSection(
            modifier = Modifier.clickable {
                actor(AboutAction.ShowPrivacyPolicy)
            },
            title = stringResource(id = R.string.about_app_privacy_policy),
            content = stringResource(id = R.string.about_app_privacy_button)
        ) {
            Icon(
                imageVector = Icons.Filled.OpenInNew,
                contentDescription = stringResource(id = R.string.about_app_privacy_button),
                tint = MaterialTheme.colors.primary,
            )
        }
        AboutSection(
            title = stringResource(
                id = R.string.about_app_version
            ),
            content = BuildConfig.VERSION_NAME
        )
    }
}

@Composable
private fun AboutSection(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable () -> Unit = {},
) {
    AboutRow(modifier) {
        TitleContent(title = title, content = content)
        trailingIcon()
    }
    Divider()
}

@Composable
private fun AboutRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        content()
    }
}

@Composable
private fun UserSection(
    userSection: UserSectionState,
) {
    Text(
        text = stringResource(id = R.string.about_app_user_section_title),
        style = Typography.h6
    )
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        when (userSection.uiState) {
            UiState.Content -> {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row {
                        TitleContent(title = stringResource(id = R.string.about_app_username_title), content = userSection.username)
                    }
                    Row {
                        TitleContent(title = stringResource(id = R.string.about_app_email_title), content = userSection.email)
                    }
                }
            }
            is UiState.Error -> ErrorMarker()
            UiState.Loading -> CircularProgressIndicator()
        }
    }
    Divider()
}

@Composable
private fun RowScope.TitleContent(
    title: String,
    content: String,
) {
    Text(
        style = Typography.subtitle1,
        modifier = Modifier.weight(1F),
        text = title
    )
    Text(text = content)
}
