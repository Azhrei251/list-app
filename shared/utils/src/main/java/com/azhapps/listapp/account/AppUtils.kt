package com.azhapps.listapp.account

import android.content.Context
import android.content.Intent
import kotlin.system.exitProcess

object AppUtils {
    fun triggerRebirth(context: Context = ApplicationModule.applicationContext) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        exitProcess(0)
    }
}