package com.azhapps.listapp.network.model

import com.azhapps.listapp.network.BuildConfig

sealed class Environment(
    val baseUrl: String,
    val clientId: String,
    val clientSecret: String,
) {
    object Test: Environment(
        baseUrl = BuildConfig.TEST_BASE_URL,
        clientId = BuildConfig.TEST_CLIENT_ID,
        clientSecret = BuildConfig.TEST_CLIENT_SECRET,
    )

    object Prod: Environment(
        baseUrl = BuildConfig.PROD_BASE_URL,
        clientId = BuildConfig.PROD_CLIENT_ID,
        clientSecret = BuildConfig.PROD_CLIENT_SECRET,
    )

    companion object {
        var currentlySelected: Environment = if (BuildConfig.DEBUG) Test else Prod
    }
}
