package com.azhapps.listapp.more.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.DialogButton
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.more.MoreViewModel
import com.azhapps.listapp.more.R
import com.azhapps.listapp.more.model.MoreAction
import com.azhapps.listapp.more.model.MoreLineState
import com.azhapps.listapp.navigation.More
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@Composable
@ExperimentalComposableDestination
@NavigationDestination(More::class)
fun MoreScreen() {
    val viewModel = viewModel<MoreViewModel>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.more_title),
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            MoreContent(
                moreItems = state.items,
                actor = viewModel::dispatch
            )

            if (state.showLogoutConfirmDialog) {
                LogoutDialog(viewModel::dispatch)
            }
        }
    }
}

@Composable
fun MoreContent(
    moreItems: List<MoreLineState>,
    actor: (MoreAction) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            moreItems.forEach {
                item {
                    MoreRow(moreItem = it, actor = actor)
                    Divider()
                }
            }
        }
    )
}

@Composable
fun MoreRow(
    moreItem: MoreLineState,
    actor: (MoreAction) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier
            .weight(1F)
            .clickable {
                actor(moreItem.action)
            }
        ) {
            Text(
                text = stringResource(id = moreItem.title),
                style = Typography.h6,
            )
            if (moreItem.subtitle != -1) {
                Text(
                    text = stringResource(id = moreItem.subtitle),
                    style = Typography.subtitle1,
                )
            }
        }
        Icon(imageVector = moreItem.icon, contentDescription = null)
    }
}

@Composable
fun LogoutDialog(
    actor: (MoreAction) -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            actor(MoreAction.CloseLogout)
        },
        confirmButton = {
            DialogButton(
                action = {
                    actor(MoreAction.DoLogout)
                },
                text = stringResource(id = R.string.more_dialog_confirm_button)
            )
        },
        title = {
            Text(text = stringResource(id = R.string.more_dialog_confirm_logout_title))
        },
        text = {
            Text(text = stringResource(id = R.string.more_dialog_confirm_logout_text))
        }
    )
}