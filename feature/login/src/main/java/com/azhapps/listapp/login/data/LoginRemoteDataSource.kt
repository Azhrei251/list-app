package com.azhapps.listapp.login.data

import com.azhapps.listapp.network.auth.model.AuthToken
import com.azhapps.listapp.network.auth.model.GrantType
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginRemoteDataSource {

    @FormUrlEncoded
    @POST("auth/token/")
    suspend fun getAuthToken(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String = GrantType.PASSWORD.value
    ): Response<AuthToken>
}