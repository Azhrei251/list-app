package com.azhapps.listapp.network

import com.azhapps.listapp.network.model.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class EnvironmentInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest
            .newBuilder()
            .url(Environment.currentlySelected.baseUrl
                    + originalRequest.url.pathSegments.joinToString(separator = "/", prefix = "/"))
            .build()

        return chain.proceed(newRequest)
    }
}