package com.azhapps.listapp.more

import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.more.ui.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(Settings::class)
class SettingsActivity : BaseActivity() {

    private val viewModel: SettingsViewModel by enroViewModels()

    @Composable
    override fun InitialContent() {
        SettingsScreen(viewModel = viewModel)
    }
}