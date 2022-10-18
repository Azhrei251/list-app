package com.azhapps.listapp.auth.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.auth.login.LoginViewModel
import com.azhapps.listapp.login.R
import com.azhapps.listapp.auth.login.model.LoginAction
import com.azhapps.listapp.auth.navigation.Login
import com.azhapps.listapp.auth.ui.PasswordField
import com.azhapps.listapp.auth.ui.UsernameField
import com.azhapps.listapp.common.ui.ContentWithDialogs
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(Login::class)
fun LoginScreen() {
    val viewModel = viewModel<LoginViewModel>()
    val navigationHandle = navigationHandle<Login>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.auth_title_log_in),
                backAction = {
                    navigationHandle.close()
                },
                showBackArrow = true
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            ContentWithDialogs(
                uiState = state.uiState,
                onErrorDismiss = { viewModel.dispatch(LoginAction.DismissErrorPopup) },
                onErrorButton = { viewModel.dispatch(LoginAction.DismissErrorPopup) }
            ) {
                LoginContent(actor = viewModel::dispatch)
            }
        }
    }
}

@Composable
fun LoginContent(
    actor: (LoginAction) -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8F)
                .padding(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = Typography.body1,
                text = stringResource(id = R.string.auth_call_to_action)
            )

            UsernameField(
                username = username,
                onUsernameChange = { username = it }
            )

            PasswordField(
                password = password,
                onPasswordChange = { password = it }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { actor(LoginAction.GetAuthToken(username, password)) }
            ) {
                Text(stringResource(id = R.string.auth_button_primary))
            }

            Spacer(modifier = Modifier.weight(1F))

            RegistrationCallToAction(actor = actor)
        }
    }
}

@Composable
private fun RegistrationCallToAction(
    actor: (LoginAction) -> Unit,
) {
    Row(
        modifier = Modifier.padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            style = Typography.body2,
            text = stringResource(id = R.string.auth_call_to_action_no_acc),
        )

        Spacer(modifier = Modifier.weight(1F))

        Button(
            onClick = { actor(LoginAction.NavigateToRegistration) }
        ) {
            Text(stringResource(id = R.string.auth_button_secondary))
        }
    }
}
