package com.azhapps.listapp.main.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.azhapps.listapp.common.BaseActivity
import com.azhapps.listapp.main.navigation.Splash
import com.azhapps.listapp.navigation.Initial
import dagger.hilt.android.AndroidEntryPoint
import dev.enro.annotations.NavigationDestination
import dev.enro.core.NavigationKey
import dev.enro.core.close
import dev.enro.core.compose.EmptyBehavior
import dev.enro.core.forward
import dev.enro.core.navigationHandle

@AndroidEntryPoint
@NavigationDestination(Initial::class)
class SplashActivity : BaseActivity() {

    private val navigationHandle by navigationHandle<Initial>()
    private var hasLaunched = false

    override val emptyBehavior = EmptyBehavior.CloseParent
    override val navigationKeyFilter: (NavigationKey) -> Boolean = { false }

    @Composable
    override fun InitialContent() {
        LaunchedEffect(Unit) {
            navigationHandle.forward(Splash)
            hasLaunched = true
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasLaunched) {
            navigationHandle.close()
        }
    }
}
