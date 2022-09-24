package com.azhapps.listapp.login

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.login.model.LoginAction
import com.azhapps.listapp.navigation.Login
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.navigationHandle
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(Login::class)
class LoginActivity : BaseActivity() {
    private val viewModel by enroViewModels<LoginViewModel>()
    private val navigationHandle by navigationHandle<Login>()

    override var uiState: UiState = UiState.Content
    override fun getToolbarTitle() = getString(R.string.login_title)
    override fun backAction(): () -> Unit = navigationHandle::close

    @Composable
    override fun Content() {
        var username by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        TextField(
            value = username,
            onValueChange = {
                username = it
            }, label = {
                Text(text = stringResource(id = R.string.login_label_username))
            }, modifier = Modifier.padding(end = 8.dp),
            singleLine = true
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
            }, label = {
                Text(text = stringResource(id = R.string.login_label_password))
            }, modifier = Modifier.padding(end = 8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true
        )

        Button(onClick = { viewModel.dispatch(LoginAction.GetAuthToken(username, password)) }) {
            Text(stringResource(id = R.string.login_button))
        }
    }
}