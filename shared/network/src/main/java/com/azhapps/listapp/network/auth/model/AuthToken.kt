package com.azhapps.listapp.network.auth.model

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiryTimeInSeconds: Int,
)