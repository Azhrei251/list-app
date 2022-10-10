package com.azhapps.listapp.main.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.azhapps.listapp.main.navigation.Splash
import com.azhapps.listapp.main.splash.SplashViewModel
import dev.enro.annotations.ExperimentalComposableDestination
import dev.enro.annotations.NavigationDestination

@Composable
@ExperimentalComposableDestination
@NavigationDestination(Splash::class)
fun SplashScreen() {
    val viewModel = viewModel<SplashViewModel>()

    LaunchedEffect(Unit) {
        viewModel.proceed()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = com.azhapps.listapp.account.R.drawable.ic_launcher),
            contentDescription = null
        )
    }
}