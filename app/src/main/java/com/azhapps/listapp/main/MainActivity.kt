package com.azhapps.listapp.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.azhapps.listapp.R
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.main.ui.MainScreen
import com.azhapps.listapp.navigation.Lists
import com.azhapps.listapp.navigation.Main
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.navigationHandle
import dev.enro.core.replace
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(Main::class)
class MainActivity : BaseActivity() {

    private val navigationHandle by navigationHandle<Main>()
    private val viewModel by enroViewModels<MainViewModel>()

    override var uiState: UiState = UiState.Content
    override val shouldShowBackArrow = false
    override fun backAction(): () -> Unit = navigationHandle::close
    override fun getToolbarTitle() = getString(R.string.app_name)//TODO

    @Composable
    override fun Content() {
        val state = viewModel.collectAsState()
        uiState = state.uiState
        if (state.done) {
            navigationHandle.replace(Lists)
        }

        MainScreen(
            actor = viewModel::dispatch
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreen({})
}