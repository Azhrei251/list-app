package com.azhapps.listapp.groups.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.groups.GroupsViewModel
import com.azhapps.listapp.navigation.Groups
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@Composable
@ExperimentalComposableDestination
@NavigationDestination(Groups::class)
fun GroupsScreen() {
    val viewModel = viewModel<GroupsViewModel>()

    Box(modifier = Modifier.fillMaxSize().background(Color.Blue)) {

    }
}