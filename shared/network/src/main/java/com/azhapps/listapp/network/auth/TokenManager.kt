package com.azhapps.listapp.network.auth

import android.accounts.Account
import android.accounts.AccountManager
import com.azhapps.listapp.account.BuildConfig
import com.azhapps.listapp.account.SelectedAccount
import com.azhapps.listapp.network.auth.model.AuthToken
import javax.inject.Inject


private const val AUTH_TOKEN_TYPE = "oauth"
private const val REFRESH_TOKEN_TYPE = "oauth_refresh"
private const val EXPIRY_KEY = "token_expiry"

class TokenManager(val accountManager: AccountManager) {

    fun hasAccount(
        accountName: String? = SelectedAccount.currentAccountName,
    ): Boolean = accountManager.accounts.any {
        it.type == BuildConfig.ACCOUNT_TYPE && it.name == accountName
    }

    fun getAuthToken(
        accountName: String? = SelectedAccount.currentAccountName,
    ): String? = getToken(AUTH_TOKEN_TYPE)

    fun getRefreshToken(
        accountName: String? = SelectedAccount.currentAccountName,
    ): String? = getToken(REFRESH_TOKEN_TYPE)

    private fun getToken(
        tokenType: String,
        accountName: String? = SelectedAccount.currentAccountName,
    ): String? {
        val account = getAccount(accountName)
        return if (account == null) null else accountManager.peekAuthToken(account, tokenType)
    }

    fun getExpiryTime(
        accountName: String? = SelectedAccount.currentAccountName,
    ): Long {
        val account = getAccount(accountName)
        return if (account == null) -1 else accountManager.getUserData(account, EXPIRY_KEY).toLong()
    }

    fun setAuthToken(
        authToken: AuthToken,
        accountName: String? = SelectedAccount.currentAccountName,
    ) {
        var account = getAccount(accountName)
        if (account == null) {
            account = Account(accountName, BuildConfig.ACCOUNT_TYPE)
            accountManager.addAccountExplicitly(account, "", null)
        }
        accountManager.setAuthToken(account, AUTH_TOKEN_TYPE, authToken.accessToken)
        accountManager.setAuthToken(account, REFRESH_TOKEN_TYPE, authToken.refreshToken)

        val expiryTime = System.currentTimeMillis() + (authToken.expiryTimeInSeconds * 1000)
        accountManager.setUserData(account, EXPIRY_KEY, expiryTime.toString())
    }

    private fun getAccount(
        accountName: String? = SelectedAccount.currentAccountName,
    ): Account? = accountManager.accounts.firstOrNull {
        it.type == BuildConfig.ACCOUNT_TYPE && it.name == accountName
    }
}