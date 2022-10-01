package com.azhapps.listapp.lists.data

import com.azhapps.listapp.lists.edit.model.CreateListCategoryRequest
import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.lists.model.Group
import com.azhapps.listapp.lists.model.InformativeList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListsRemoteDataSource {

    @GET("lists")
    suspend fun getOwnLists(): Response<List<InformativeList>>

    @POST("lists/create")
    suspend fun createList(
        @Body request: InformativeList
    ): Response<Unit>

    @PUT("lists/{listId}")
    suspend fun updateList(
        @Path("listId") listId: Int,
        @Body request: InformativeList
    ): Response<InformativeList>

    @DELETE("lists/{listId}")
    suspend fun deleteList(
        @Path("listId") listId: Int,
        @Body request: InformativeList
    ): Response<Unit>

    @GET("lists/{listId}")
    suspend fun getListById(
        @Path("listId") listId: Int
    ): Response<InformativeList>

    @GET("lists/category")
    suspend fun getListCategories(): Response<List<Category>>

    @GET("lists/category/{categoryId}")
    suspend fun getListCategoryById(
        @Path("categoryId") categoryId: Int
    ): Response<Category>

    @POST("lists/category")
    suspend fun createListCategory(
        @Body request: CreateListCategoryRequest
    ): Response<Category>

    @GET("users/groups")
    suspend fun getGroups(): Response<List<Group>>
}