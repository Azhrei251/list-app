package com.azhapps.listapp.landing.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.common.ui.ThemedScaffold
import com.azhapps.listapp.groups.ui.GroupsScreen
import com.azhapps.listapp.landing.LandingViewModel
import com.azhapps.listapp.landing.model.LandingAction
import com.azhapps.listapp.landing.model.LandingState
import com.azhapps.listapp.lists.selection.ui.ListSelectionScreen
import com.azhapps.listapp.more.ui.MoreScreen
import com.azhapps.listapp.navigation.Landing
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.close
import dev.enro.core.compose.navigationHandle


@Composable
@ExperimentalComposableDestination
@NavigationDestination(Landing::class)
fun LandingScreen() {
    val viewModel = viewModel<LandingViewModel>()
    val state = viewModel.collectAsState()
    val navigationHandle = navigationHandle<Landing>()

    ThemedScaffold(
        bottomBar = {
            LandingBottomNavigation(viewModel::dispatch, state.currentTab)
        }
    ) {
        Box(Modifier.padding(it)) {
            LandingContent(state.currentTab)
        }
        BackHandler {
            if (state.currentTab != LandingState.Tab.LISTS) {
                viewModel.dispatch(LandingAction.GoToTab(LandingState.Tab.LISTS))
            } else {
                navigationHandle.close()
            }
        }
    }
}

@Composable
fun LandingContent(
    currentTab: LandingState.Tab,
) {
    Column(Modifier.fillMaxSize()) {
        when (currentTab) {
            LandingState.Tab.LISTS -> ListSelectionScreen()
            LandingState.Tab.GROUPS -> GroupsScreen()
            LandingState.Tab.MORE -> MoreScreen()
        }
    }
}

@Composable
private fun LandingBottomNavigation(
    actor: (LandingAction) -> Unit,
    currentTab: LandingState.Tab,
) {
    BottomNavigation {
        LandingState.Tab.values().forEach {
            BottomNavigationItem(
                selected = it == currentTab,
                onClick = { actor(LandingAction.GoToTab(it)) },
                icon = {
                    Icon(painter = painterResource(id = it.icon), contentDescription = stringResource(id = it.buttonText))
                },
                label = {
                    Text(stringResource(id = it.buttonText))
                },
            )
        }
    }
}

