package com.azhapps.listapp.groups.data

import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.common.model.User
import com.azhapps.listapp.groups.model.CreateGroupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GroupsRemoteDataSource {

    @POST("users/groups")
    suspend fun createGroup(
        @Body createGroupRequest: CreateGroupRequest
    ): Response<Group>

    @PUT("users/group/{groupId}")
    suspend fun updateGroup(
        @Path("groupId") groupId: Int,
        @Body group: Group
    ): Response<Group>

    @PUT("users/group/{groupId}/member/{userId}")
    suspend fun addUserToGroup(
        @Path("groupId") groupId: Int,
        @Path("userId") userId: Int,
    ): Response<Unit>

    @DELETE("users/group/{groupId}/member/{userId}")
    suspend fun removeUserFromGroup(
        @Path("groupId") groupId: Int,
        @Path("userId") userId: Int,
    ): Response<Unit>

    @GET("users/search")
    suspend fun searchUser(
        @Query("filter") filter: String,
    ): Response<List<User>>
}