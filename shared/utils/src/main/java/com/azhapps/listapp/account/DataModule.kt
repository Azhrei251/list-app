package com.azhapps.listapp.account

import android.accounts.AccountManager
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val SHARED_PREFERENCES_PATH = "some_preferences_path"
    const val DEFAULT_GROUP_KEY = "default_group_key"

    @Provides
    fun provideSharedPreferences(
        applicationContext: Context
    ): SharedPreferences = applicationContext.getSharedPreferences(SHARED_PREFERENCES_PATH, MODE_PRIVATE)

    @Provides
    fun provideAccountManager(
        applicationContext: Context
    ): AccountManager {
        return AccountManager.get(applicationContext)
    }
}