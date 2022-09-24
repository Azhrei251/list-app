package com.azhapps.listapp.account

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    lateinit var applicationContext: Context

    @Provides
    fun provideContext(): Context {
        return applicationContext
    }
}