package com.azhapps.listapp.network.data

import com.azhapps.listapp.network.auth.model.AuthToken
import com.azhapps.listapp.network.auth.model.GrantType
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthRemoteDataSource {

    @FormUrlEncoded
    @POST("auth/token/")
    suspend fun refreshAuthToken(
        @Field("refresh_token") refreshToken: String,
        @Field("grant_type") grantType: String = GrantType.REFRESH.value
    ): Response<AuthToken>
}