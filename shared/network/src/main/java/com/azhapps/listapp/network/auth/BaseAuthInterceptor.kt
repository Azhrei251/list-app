package com.azhapps.listapp.network.auth

import android.os.Build
import android.util.Base64
import com.azhapps.listapp.network.BuildConfig
import com.azhapps.listapp.network.model.Environment
import okhttp3.Interceptor

abstract class BaseAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", getAuthHeaderValue())
            .addHeader("User-Agent", getUserAgentString())
            .build()
    )

    abstract fun getAuthHeaderValue(): String

    protected fun getBasicAuthValue(
        environment: Environment
    ): String = "Basic " + Base64.encodeToString("${environment.clientId}:${environment.clientSecret}".toByteArray(), Base64.NO_WRAP)

    private fun getUserAgentString(): String {
        val flavor = if (BuildConfig.DEBUG) "Debug" else "Release"
        return "List App $flavor ${BuildConfig.VERSION_NAME}/Android ${Build.VERSION.RELEASE}"
    }
}