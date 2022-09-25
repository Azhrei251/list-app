package com.azhapps.listapp.account.account

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticationService : Service() {

    private var authenticator: AccountAuthenticator? = null

    override fun onCreate() {
        authenticator = AccountAuthenticator(this)
    }

    override fun onBind(
        intent: Intent
    ) = authenticator?.iBinder
}