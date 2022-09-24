package com.azhapps.listapp.network.auth.model

enum class GrantType(
    val value: String
) {
    PASSWORD("password"),
    REFRESH("refresh_token"),
}