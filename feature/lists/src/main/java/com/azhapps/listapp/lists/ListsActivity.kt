package com.azhapps.listapp.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.common.UiState
import com.azhapps.listapp.lists.navigation.ListSelection
import com.azhapps.listapp.navigation.Lists
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationInstruction
import dev.enro.core.close
import dev.enro.core.compose.EnroContainer
import dev.enro.core.compose.rememberEnroContainerController
import dev.enro.core.navigationHandle
import dev.enro.viewmodel.enroViewModels

@AndroidEntryPoint
@NavigationDestination(Lists::class)
class ListsActivity : BaseActivity() {
    private val navigationHandle by navigationHandle<Lists>()
    private val viewModel by enroViewModels<ListsViewModel>()

    override var uiState: UiState = UiState.Content
    override val shouldShowBackArrow = false
    override fun getToolbarTitle() = getString(R.string.lists_selection_title)
    override fun backAction() = navigationHandle::close

    @Composable
    override fun Content() {
        val containerController = rememberEnroContainerController(
            initialState = listOf(NavigationInstruction.Forward(ListSelection)),
        )
        Column(Modifier.fillMaxSize()) {
            EnroContainer(
                controller = containerController
            )
        }
    }
}