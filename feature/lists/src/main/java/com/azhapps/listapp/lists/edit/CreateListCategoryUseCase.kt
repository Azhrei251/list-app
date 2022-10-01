package com.azhapps.listapp.lists.edit

import com.azhapps.listapp.lists.data.ListsRemoteDataSource
import com.azhapps.listapp.lists.edit.model.CreateListCategoryRequest
import com.azhapps.listapp.network.Api.callApi
import javax.inject.Inject

class CreateListCategoryUseCase @Inject constructor(
    private val remoteDataSource: ListsRemoteDataSource,
) {
    suspend operator fun invoke(name: String) = callApi {
        remoteDataSource.createListCategory(CreateListCategoryRequest(name))
    }
}