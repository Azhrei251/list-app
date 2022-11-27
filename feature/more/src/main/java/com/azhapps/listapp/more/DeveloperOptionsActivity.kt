package com.azhapps.listapp.more

import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.more.ui.DeveloperOptionsScreen
import com.azhapps.listapp.navigation.DeveloperOptions
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(DeveloperOptions::class)
class DeveloperOptionsActivity : BaseActivity() {

    private val viewModel: DeveloperOptionsViewModel by enroViewModels()

    @Composable
    override fun InitialContent() {
        DeveloperOptionsScreen(viewModel = viewModel)
    }
}