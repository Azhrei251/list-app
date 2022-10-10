package com.azhapps.listapp.lists.data

import com.azhapps.listapp.lists.model.Category
import com.azhapps.listapp.common.model.Group
import com.azhapps.listapp.lists.model.InformativeList
import com.azhapps.listapp.lists.model.ListItem
import com.azhapps.listapp.lists.modify.model.CreateCategoryRequest
import com.azhapps.listapp.lists.selection.model.CreateListRequest
import com.azhapps.listapp.lists.view.model.CreateListItemRequest
import com.azhapps.listapp.lists.view.model.ModifyListItemRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ListsRemoteDataSource {

    @GET("lists")
    suspend fun getOwnLists(): Response<List<InformativeList>>

    @POST("lists/create")
    suspend fun createList(
        @Body request: CreateListRequest
    ): Response<InformativeList>

    @PUT("lists/{listId}")
    suspend fun updateList(
        @Path("listId") listId: Int,
        @Body request: InformativeList
    ): Response<InformativeList>

    @DELETE("lists/{listId}")
    suspend fun deleteList(
        @Path("listId") listId: Int,
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
        @Body request: CreateCategoryRequest
    ): Response<Category>

    @GET("users/groups")
    suspend fun getGroups(): Response<List<Group>>

    @POST("lists/item")
    suspend fun createListItem(
        @Body request: CreateListItemRequest
    ): Response<ListItem>

    @GET("lists/item/category")
    suspend fun getItemCategories(): Response<List<Category>>

    @POST("lists/item/category")
    suspend fun createItemCategory(
        @Body request: CreateCategoryRequest
    ): Response<Category>

    @PUT("lists/item/{itemId}")
    suspend fun updateItem(
        @Path("itemId") itemId: Int,
        @Body request: ModifyListItemRequest
    ): Response<ListItem>

    @DELETE("lists/item/{itemId}")
    suspend fun deleteListItem(
        @Path("itemId") itemId: Int,
    ): Response<Unit>
}