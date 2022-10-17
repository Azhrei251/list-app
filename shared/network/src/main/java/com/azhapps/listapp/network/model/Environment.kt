package com.azhapps.listapp.network.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.annotation.StringRes
import com.azhapps.listapp.account.AppUtils.triggerRebirth
import com.azhapps.listapp.account.ApplicationModule
import com.azhapps.listapp.account.DataModule
import com.azhapps.listapp.network.BuildConfig
import com.azhapps.listapp.network.R
import kotlin.system.exitProcess


sealed class Environment(
    val baseUrl: String,
    val clientId: String,
    val clientSecret: String,
    @StringRes val displayName: Int,
    val tag: String,
) {
    object Test : Environment(
        baseUrl = BuildConfig.TEST_BASE_URL,
        clientId = BuildConfig.TEST_CLIENT_ID,
        clientSecret = BuildConfig.TEST_CLIENT_SECRET,
        displayName = R.string.environment_test_name,
        tag = "prod",
    )

    object Prod : Environment(
        baseUrl = BuildConfig.PROD_BASE_URL,
        clientId = BuildConfig.PROD_CLIENT_ID,
        clientSecret = BuildConfig.PROD_CLIENT_SECRET,
        displayName = R.string.environment_prod_name,
        tag = "test",
    )

    companion object {
        private const val SELECTED_ENVIRONMENT_KEY = "SELECTED_ENVIRONMENT_KEY"
        var currentlySelected: Environment = if (BuildConfig.DEBUG) Test else Prod
            private set

        fun init(context: Context) {
            DataModule.provideSharedPreferences(context).getString(SELECTED_ENVIRONMENT_KEY, null)?.let {
                currentlySelected = when (it) {
                    Test.tag -> Test
                    Prod.tag -> Prod
                    else -> currentlySelected
                }
            }
        }

        fun set(sharedPreferences: SharedPreferences, environment: Environment) {
            currentlySelected = environment
            sharedPreferences.edit().putString(SELECTED_ENVIRONMENT_KEY, environment.tag).commit()
            triggerRebirth(ApplicationModule.applicationContext)
        }
    }
}
