package com.azhapps.listapp.network.auth

import com.azhapps.listapp.network.model.Environment

class BasicAuthInterceptor: BaseAuthInterceptor() {
    override fun getAuthHeaderValue() = getBasicAuthValue(Environment.currentlySelected)
}