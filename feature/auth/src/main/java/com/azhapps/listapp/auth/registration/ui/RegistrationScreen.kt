package com.azhapps.listapp.auth.registration.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.TopBar
import com.azhapps.listapp.common.ui.theme.Typography
import com.azhapps.listapp.login.R
import com.azhapps.listapp.auth.login.model.LoginAction
import com.azhapps.listapp.auth.navigation.Register
import com.azhapps.listapp.auth.registration.RegistrationViewModel
import com.azhapps.listapp.auth.registration.model.RegistrationAction
import com.azhapps.listapp.auth.ui.EmailField
import com.azhapps.listapp.auth.ui.PasswordField
import com.azhapps.listapp.auth.ui.UsernameField
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.common.ui.ContentWithDialogs
import com.azhapps.listapp.common.ui.DialogButton
import com.azhapps.listapp.common.ui.ErrorPage
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle

@Composable
@ExperimentalComposableDestination
@NavigationDestination(Register::class)
fun RegistrationScreen() {
    val viewModel = viewModel<RegistrationViewModel>()
    val navigationHandle = navigationHandle<Register>()
    val state = viewModel.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(R.string.auth_title_register),
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
                onErrorDismiss = { viewModel.dispatch(RegistrationAction.DismissErrorPopup) },
                onErrorButton = { viewModel.dispatch(RegistrationAction.DismissErrorPopup) }
            ) {
                RegistrationContent(
                    actor = viewModel::dispatch,
                )
            }
        }
    }
}

@Composable
fun RegistrationContent(
    actor: (RegistrationAction) -> Unit,
) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

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
                text = stringResource(id = R.string.auth_registration_call_to_action)
            )

            UsernameField(
                username = username,
                onUsernameChange = { username = it }
            )

            PasswordField(
                password = password,
                onPasswordChange = { password = it }
            )

            EmailField(
                email = email,
                onEmailChange = { email = it }
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { actor(RegistrationAction.RegisterAccount(username, password, email)) }
            ) {
                Text(stringResource(id = R.string.auth_button_secondary))
            }
        }
    }
}