package com.azhapps.listapp

import android.app.Application
import android.content.res.Configuration
import com.azhapps.listapp.account.ApplicationModule
import com.azhapps.listapp.common.ui.theme.ListAppTheme
import com.azhapps.listapp.network.model.Environment
import dagger.hilt.android.HiltAndroidApp
import dev.enro.annotations.NavigationComponent
import dev.enro.core.controller.NavigationApplication
import dev.enro.core.controller.NavigationController
import dev.enro.core.controller.navigationController

@HiltAndroidApp
@NavigationComponent
class ListApp: Application(), NavigationApplication {
    override val navigationController: NavigationController = navigationController()

    override fun onCreate() {
        super.onCreate()
        ApplicationModule.applicationContext = applicationContext
        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        ListAppTheme.init(isDarkMode)
        Environment.init(applicationContext)
    }
}