package com.azhapps.listapp.account

import android.content.SharedPreferences

object SelectedAccount {

    private const val CURRENT_ACCOUNT_KEY = "current_account"

    var currentAccountName: String? = null

    fun init(sharedPreferences: SharedPreferences) {
        currentAccountName = sharedPreferences.getString(CURRENT_ACCOUNT_KEY, null)
    }

    fun update(
        newAccountName: String,
        sharedPreferences: SharedPreferences
    ) {
        currentAccountName = newAccountName
        sharedPreferences.edit().putString(CURRENT_ACCOUNT_KEY, currentAccountName).apply()
    }
}