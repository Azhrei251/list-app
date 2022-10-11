package com.azhapps.listapp.common.data

import com.azhapps.listapp.common.model.Group
import retrofit2.Response
import retrofit2.http.GET

interface CommonRemoteDataSource {

    @GET("users/groups")
    suspend fun getGroups(): Response<List<Group>>
}