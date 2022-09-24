package com.azhapps.listapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.enro.annotations.NavigationComponent
import dev.enro.core.controller.NavigationApplication
import dev.enro.core.controller.NavigationController
import dev.enro.core.controller.navigationController

@HiltAndroidApp
@NavigationComponent
class ListApp: Application(), NavigationApplication {
    override val navigationController: NavigationController = navigationController()
}