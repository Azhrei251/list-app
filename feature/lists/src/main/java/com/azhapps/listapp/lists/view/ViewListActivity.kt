package com.azhapps.listapp.lists.view

import androidx.compose.runtime.Composable
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.lists.navigation.ViewList
import com.azhapps.listapp.lists.view.ui.ViewListScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(ViewList::class)
class ViewListActivity : BaseActivity() {

    private val viewModel: ViewListViewModel by enroViewModels()

    @Composable
    override fun InitialContent() {
        ViewListScreen(viewModel = viewModel)
    }
}