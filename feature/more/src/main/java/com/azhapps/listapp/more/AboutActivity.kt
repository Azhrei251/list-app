package com.azhapps.listapp.more

import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.more.ui.AboutScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(About::class)
class AboutActivity : BaseActivity() {

    private val viewModel: AboutViewModel by enroViewModels()

    @Composable
    override fun InitialContent() {
        AboutScreen(viewModel = viewModel)
    }
}