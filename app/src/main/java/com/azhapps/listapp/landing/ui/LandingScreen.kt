package com.azhapps.listapp.landing.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.landing.LandingViewModel
import com.azhapps.listapp.landing.model.LandingAction
import com.azhapps.listapp.landing.model.LandingState
import com.azhapps.listapp.main.navigation.Landing
import com.azhapps.listapp.navigation.Groups
import com.azhapps.listapp.navigation.Lists
import com.azhapps.listapp.navigation.More
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationInstruction
import dev.enro.core.compose.EmptyBehavior
import dev.enro.core.compose.EnroContainer
import dev.enro.core.compose.EnroContainerController
import dev.enro.core.compose.rememberEnroContainerController

@Composable
@ExperimentalComposableDestination
@NavigationDestination(Landing::class)
fun LandingScreen() {
    val viewModel = viewModel<LandingViewModel>()
    val containerController = rememberEnroContainerController(
        initialState = listOf(NavigationInstruction.Forward(Lists)),
        emptyBehavior = EmptyBehavior.CloseParent,
    )
    val state = viewModel.collectAsState()

    containerController.push(
        when (state.currentTab) {
            LandingState.Tab.LISTS -> NavigationInstruction.Replace(Lists)
            LandingState.Tab.GROUPS -> NavigationInstruction.Replace(Groups)
            LandingState.Tab.MORE -> NavigationInstruction.Replace(More)
        }
    )

    Scaffold(
        bottomBar = {
            LandingBottomNavigation(viewModel::dispatch, state.currentTab)
        }
    ) {
        Box(Modifier.padding(it)) {
            LandingContent(containerController = containerController)
        }
    }
}

@Composable
fun LandingContent(containerController: EnroContainerController) {
    Column(Modifier.fillMaxSize()) {
        EnroContainer(
            controller = containerController
        )
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

